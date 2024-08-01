import Code from "@tiptap/extension-code";
import CodeBlock from "@tiptap/extension-code-block";
import { Color } from "@tiptap/extension-color";
import FontFamily from "@tiptap/extension-font-family";
import { Image } from "@tiptap/extension-image";
import { Placeholder } from "@tiptap/extension-placeholder";
import Strike from "@tiptap/extension-strike";
import Subscript from "@tiptap/extension-subscript";
import Superscript from "@tiptap/extension-superscript";
import { Table } from "@tiptap/extension-table";
import TableCell from "@tiptap/extension-table-cell";
import TableHeader from "@tiptap/extension-table-header";
import TableRow from "@tiptap/extension-table-row";
import TextAlign from "@tiptap/extension-text-align";
import TextStyle from "@tiptap/extension-text-style";
import Underline from "@tiptap/extension-underline";
import StarterKit from "@tiptap/starter-kit";
import {
  FontSize,
  MenuButtonAddTable,
  MenuButtonAlignCenter,
  MenuButtonAlignJustify,
  MenuButtonAlignLeft,
  MenuButtonAlignRight,
  MenuButtonBlockquote,
  MenuButtonBold,
  MenuButtonBulletedList,
  MenuButtonCode,
  MenuButtonCodeBlock,
  MenuButtonColorPicker,
  MenuButtonEditLink,
  MenuButtonImageUpload,
  MenuButtonItalic,
  MenuButtonOrderedList,
  MenuButtonRedo,
  MenuButtonStrikethrough,
  MenuButtonSubscript,
  MenuButtonSuperscript,
  MenuButtonTextColor,
  MenuButtonUnderline,
  MenuButtonUndo,
  MenuControlsContainer,
  MenuDivider,
  MenuSelectFontFamily,
  MenuSelectFontSize,
  MenuSelectHeading,
  RichTextEditor,
} from "mui-tiptap";
import React, { useCallback, useRef, useState } from "react";
import { pushBlog } from "../RestApi/pushBlog";

const EditBlog = () => {
  const rteRef = useRef(null);

  const [formData, setFormData] = useState({
    title: "",
    summary: "",
    authorId: "AuthorABC",
    content: rteRef.current?.editor?.getHTML(),
    tags: [],
    category: "",
  });
  const [wordCount, setWordCount] = useState(0);
  const [previewContent, setPreviewContent] = useState("");
  const [preview, setPreview] = useState(false);

  /* Handle Submit of Data */
  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      const result = await pushBlog({
        ...formData,
      });

      console.log("Blog posted successfully:", result);
    } catch (error) {
      console.error("Error posting blog:", error);
    }
  };

  const openLinkBubble = () => {};

  const insertImages = ({ images, editor }) => {
    if (editor && images && images.length > 0) {
      images.forEach((image) => {
        editor.commands.insertContent({
          type: "image",
          attrs: {
            src: image.src,
            alt: image.alt || "Image",
          },
        });
      });
    }
  };

  const handleNewImageFiles = useCallback((files) => {
    if (!rteRef.current?.editor || !files || !files.length) return;

    const imageAttributes = files.map((file) => ({
      src: URL.createObjectURL(file),
      alt: file.name,
    }));

    rteRef.current.editor
      .chain()
      .focus()
      .insertContent(
        imageAttributes.map((attr) => ({ type: "image", attrs: attr }))
      )
      .run();
  }, []);

  const previewData = () => {
    if (!preview) {
      const content = rteRef.current?.editor?.getHTML();
      setPreviewContent(content);
      setPreview(true);
    } else {
      setPreview(false);
    }
  };

  const handleChange = () => {
    const content = rteRef.current?.editor?.getText() || "";
    setWordCount(content.split(/\s+/).filter(Boolean).length);
    setFormData((prevData) => ({
      ...prevData,
      content: rteRef.current?.editor?.getHTML() || "",
    }));
  };

  const handleTagsChange = (event) => {
    setFormData((prevData) => ({
      ...prevData,
      tags: event.target.value.split(",").map((tag) => tag.trim()),
    }));
  };

  return (
    <div className="p-10">
      <h1 className="text-4xl font-bold mb-4 text-center">BLOG EDITOR</h1>
      <form onSubmit={handleSubmit} className="flex flex-col space-y-4">
        <input
          type="text"
          name="title"
          placeholder="Your Title"
          value={formData.title}
          onChange={(e) => setFormData({ ...formData, title: e.target.value })}
          className="w-full px-4 py-2 mx-2 border border-blue-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
        <input
          type="text"
          name="summary"
          placeholder="Your Summary"
          value={formData.summary}
          onChange={(e) =>
            setFormData({ ...formData, summary: e.target.value })
          }
          className="w-full px-4 py-2 mx-2 border border-blue-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
        <input
          type="text"
          name="tags"
          placeholder="Tags (comma-separated)"
          value={formData.tags.join(",")}
          onChange={handleTagsChange}
          className="w-full px-4 py-2 mx-2 border border-blue-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
        <select
          name="category"
          value={formData.category}
          onChange={(e) =>
            setFormData({ ...formData, category: e.target.value })
          }
          className="w-full px-4 py-2 mx-2 border border-blue-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
        >
          <option value="">Select Category</option>
          <option value="technology">Technology</option>
          <option value="lifestyle">Lifestyle</option>
          <option value="education">Education</option>
          <option value="health">Health</option>
        </select>

        {!preview ? (
          <>
            <RichTextEditor
              editorProps={{
                handleDOMEvents: {
                  focus: () => {
                    return false;
                  },
                },
                transformPastedHTML: (html) => {
                  return html.replace(/&nbsp;/g, " ");
                },
              }}
              ref={rteRef}
              className="p-6 border border-gray-300 rounded-lg"
              extensions={[
                Placeholder.configure({
                  placeholder:
                    "Add a longer description for your Blog By Using Extensions",
                  emptyNodeClass:
                    "first:before:text-gray-600 first:before:float-left first:before:content-[attr(data-placeholder)] first:before:pointer-events-none",
                }),
                StarterKit.configure({
                  orderedList: {
                    HTMLAttributes: {
                      class: "list-decimal pl-4",
                    },
                  },
                  bulletList: {
                    HTMLAttributes: {
                      class: "list-disc pl-4",
                    },
                  },
                }),
                TextAlign.configure({
                  types: ["heading", "paragraph"],
                  HTMLAttributes: {
                    style: "text-align: inherit;",
                  },
                }),
                Underline,
                Table.configure({
                  resizable: true,
                  allowTableNodeSelection: true,
                  lastColumnResizable: true,
                  HTMLAttributes: {
                    class: "border-gray-300",
                    style:
                      "display:flex; justify-content:center; border-collapse: collapse;",
                  },
                }),
                TableRow,
                TableCell.configure({
                  HTMLAttributes: {
                    style:
                      "align-item margin:auto; border: 1px solid #ccc; padding: 8px;",
                  },
                }),
                TableHeader.configure({
                  HTMLAttributes: {
                    style:
                      "border: 1px solid #ccc; padding: 8px; background-color: #f5f5f5;",
                  },
                }),
                Code.configure({
                  HTMLAttributes: {
                    style:
                      "color:#6e1b91; border:1px solid #0000001f; background-color:#0000000a;",
                  },
                }),
                Strike.configure({
                  HTMLAttributes: {
                    style: "color:red;",
                  },
                }),
                Subscript,
                Superscript,
                Color.configure({
                  types: ["textStyle"],
                }),
                TextStyle.configure({
                  HTMLAttributes: {
                    style: "color: inherit; background-color: inherit;",
                  },
                }),
                Image.configure({
                  HTMLAttributes: {
                    style: "margin:1rem auto; max-width:70vw; max-height:70vh;",
                  },
                }),
                CodeBlock.configure({
                  HTMLAttributes: {
                    style:
                      "margin-top:4px;margin-bottom:4px;padding:8px;border-width:1px;border-style:solid;border-color:rgba(0, 0, 0, 0.12);border-radius:4px;background:rgba(0, 0, 0, 0.04);line-height:1.4;overflow-x:auto;white-space:pre!important",
                  },
                }),
                FontSize,
                FontFamily,
              ]}
              content={formData.content}
              onUpdate={handleChange}
              renderControls={() => (
                <MenuControlsContainer>
                  <MenuSelectHeading />
                  <MenuDivider />
                  <MenuSelectFontFamily
                    options={[
                      { label: "Arial", value: "arial" },
                      { label: "Courier New", value: "courier-new" },
                      { label: "Georgia", value: "georgia" },
                      { label: "Times New Roman", value: "times-new-roman" },
                      { label: "Verdana", value: "verdana" },
                      { label: "Comic Sans MS", value: "comic-sans-ms" },
                      { label: "Helvetica", value: "helvetica" },
                      { label: "Impact", value: "impact" },
                      { label: "Tahoma", value: "tahoma" },
                      { label: "Trebuchet MS", value: "trebuchet-ms" },
                      { label: "Lucida Sans", value: "lucida-sans" },
                      { label: "Fira Code", value: "fira-code" },
                      { label: "Roboto Mono", value: "roboto-mono" },
                      { label: "SF UI Text", value: "sf-ui-text" },
                      { label: "Hack", value: "hack" },
                      { label: "Dancing Script", value: "dancing-script" },
                    ]}
                  />
                  <MenuSelectFontSize />
                  <MenuButtonBold />
                  <MenuButtonItalic />
                  <MenuButtonUnderline />
                  <MenuButtonCode />
                  <MenuButtonTextColor />
                  <MenuButtonBlockquote />
                  <MenuButtonOrderedList />
                  <MenuButtonBulletedList />
                  <MenuButtonAlignLeft />
                  <MenuButtonAlignJustify />
                  <MenuButtonAlignCenter />
                  <MenuButtonAlignRight />
                  <MenuButtonCodeBlock />
                  <MenuButtonColorPicker
                    value="Red"
                    tooltipLabel="Color Picker"
                    onChange={openLinkBubble}
                  />
                  <MenuButtonAddTable />
                  <MenuButtonEditLink onClick={openLinkBubble} />
                  <MenuButtonStrikethrough />
                  <MenuButtonSubscript />
                  <MenuButtonSuperscript />
                  <MenuButtonImageUpload
                    insertImages={insertImages}
                    onUploadFiles={handleNewImageFiles}
                  />
                  <MenuButtonRedo />
                  <MenuButtonUndo />
                </MenuControlsContainer>
              )}
            />
          </>
        ) : (
          <div className="mt-8 p-6 border border-gray-300 rounded-lg bg-gray-50">
            <h2 className="text-2xl font-bold mb-4">Blog Preview</h2>
            <div
              className="prose"
              dangerouslySetInnerHTML={{ __html: previewContent }}
            />
          </div>
        )}
        <div className="flex justify-around w-full mt-4">
          <button
            type="button"
            className="bg-blue-500 text-white px-4 py-2 mx-2 rounded hover:bg-blue-600"
            onClick={previewData}
          >
            {preview ? "Edit" : "Preview"}
          </button>
          <button
            type="submit"
            className="bg-blue-500 text-white px-4 py-2 mx-2 rounded hover:bg-blue-600"
          >
            Publish
          </button>
          <p>Word Count: {wordCount}</p>
        </div>
      </form>
    </div>
  );
};

export default EditBlog;
