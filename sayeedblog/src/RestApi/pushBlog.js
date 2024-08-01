const BASE_URL = process.env.REACT_APP_API_BASE_URL || "http://localhost:8080";

// Function to extract blob URLs from content
const extractBlobUrls = (content) => {
  const blobUrlPattern = /blob:[^\s"]+/g;
  return [...new Set(content.match(blobUrlPattern) || [])];
};

// Function to upload an image and get the file ID
const uploadImage = async (blobUrl) => {
  try {
    const response = await fetch(blobUrl);
    const blob = await response.blob();

    const formData = new FormData();
    formData.append("files", blob);

    const uploadResponse = await fetch(`${BASE_URL}/api/images/upload`, {
      method: "POST",
      body: formData,
    });

    if (!uploadResponse.ok) {
      throw new Error(`Failed to upload image: ${blobUrl}`);
    }

    const [fileId] = await uploadResponse.json();
    return fileId;
  } catch (error) {
    console.error(`Error uploading image ${blobUrl}:`, error);
    throw error;
  }
};

// Function to replace blob URLs with server URLs in content
const replaceBlobUrlsInContent = async (content) => {
  const blobUrls = extractBlobUrls(content);
  const replacements = {};
  const uploadedImageIds = [];

  for (const blobUrl of blobUrls) {
    try {
      const fileId = await uploadImage(blobUrl);
      uploadedImageIds.push(fileId);
      replacements[blobUrl] = `${BASE_URL}/api/images/${fileId}`;
    } catch {
      continue;
    }
  }

  let updatedContent = content;
  for (const [blobUrl, newUrl] of Object.entries(replacements)) {
    updatedContent = updatedContent.replace(blobUrl, newUrl);
  }

  return { updatedContent, uploadedImageIds };
};

// Function to post the blog data
export const pushBlog = async (blogData) => {
  console.log("PushBlog:", JSON.stringify(blogData));

  try {
    const { updatedContent, uploadedImageIds } = await replaceBlobUrlsInContent(
      blogData.content
    );

    const updatedBlogData = {
      ...blogData,
      content: updatedContent,
      images: uploadedImageIds,
    };

    const response = await fetch(`${BASE_URL}/post`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(updatedBlogData),
    });

    if (!response.ok) {
      throw new Error("Failed to post blog");
    }

    return await response.json();
  } catch (error) {
    console.error("Error posting blog:", error);
    throw error;
  }
};
