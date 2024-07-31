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

const EditBlog = () => {
  const rteRef = useRef(null);
  const [category, setCategory] = useState("");
  const [wordCount, setWordCount] = useState(0);
  const [previewContent, setPreviewContent] = useState("");

  const openLinkBubble = () => {};

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
    const content = rteRef.current?.editor?.getHTML();
    console.log(content);
    setPreviewContent(content);
  };

  const handleWordCount = () => {
    const content = rteRef.current?.editor?.getText() || "";
    setWordCount(content.split(/\s+/).filter(Boolean).length);
  };

  return (
    <div className="p-10">
      <h1 className="text-4xl font-bold mb-4 text-center">BLOG EDITOR</h1>
      <div className="flex w-full flex-col space-y-4 p-4">
        <input
          type="text"
          placeholder="Your Title"
          className="w-full px-4 py-2 mx-2 border border-blue-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
        <input
          type="text"
          placeholder="Your Summary"
          className="w-full px-4 py-2 mx-2 border border-blue-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
        <select
          value={category}
          onChange={(e) => setCategory(e.target.value)}
          className="w-full px-4 py-2 mx-2 border border-blue-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
        >
          <option value="">Select Category</option>
          <option value="technology">Technology</option>
          <option value="lifestyle">Lifestyle</option>
          <option value="education">Education</option>
          <option value="health">Health</option>
        </select>
      </div>

      <RichTextEditor
        editorProps={{
          handleDOMEvents: {
            focus: () => {
              console.log("Editor is focused");
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
            placeholder: "Add a longer description for your products",
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
        content='<h2 style="text-align: center">Welcome to Jemes Web Telescope! 🌟</h2><p>Explore this <em>comprehensive</em> overview of <code>our-services</code>, showcasing how Jemes Web Telescope integrates <a target="_blank" rel="noopener noreferrer nofollow" href="https://www.jemeswebtelescope.com/">advanced telescopic technology</a> with <a target="_blank" rel="noopener noreferrer nofollow" href="https://www.space.com/">cutting-edge space research</a>. From <strong>extensive <em>features</em> <s>specifications</s> <u>benefits</u></strong> to interactive <span data-type="contact" data-id="101" data-label="Support Team">@Support Team</span> interactions and detailed service lists:</p><ul><li><p>Here’s a feature highlight …</p></li><li><p>… and additional insights on our technology.</p></li></ul><p>Impressive, right? And everything is designed to enhance your viewing experience. <strong><span style="color: #007bff">But wait, </span><span style="color: #0056b3"><mark data-color="#e2e3e5" style="background-color: #e2e3e5; color: inherit">there’s even more to explore!</mark></span></strong> Check out our code implementation:</p><pre><code class="language-js">const telescopeDetails = &apos;Advanced optical systems&apos;;\nconsole.log(telescopeDetails);</code></pre><p>That&apos;s just a glimpse. You can also view and adjust various telescopic features:</p><img height="auto" src="https://picsum.photos/500/300" alt="telescope image" width="300" style="aspect-ratio: 5 / 3"><p>Organize your observations with our data tables:</p><table><tbody><tr><th><p>Feature</p></th><th><p>Description</p></th><th><p>Status</p></tr><tr><td><p>Optical Zoom</p></td><td><p>High magnification</p></td><td><p>Active</p></td></tr><tr><td><p>Image Stabilization</p></td><td><p>Enhanced clarity</p></td><td><p>Pending</p></td></tr></tbody></table><p>Or track your observational tasks:</p><ul data-type="taskList"><li data-checked="true" data-type="taskItem"><label><input type="checkbox" checked="checked"><span></span></label><div><p>Calibration</p></div></li><li data-checked="false" data-type="taskItem"><label><input type="checkbox"><span></span></label><div><p>Software update</p></div></li><li data-checked="false" data-type="taskItem"><label><input type="checkbox"><span></span></label><div><p>Lens cleaning</p></div></li></ul><blockquote><p>Great job on exploring our technology! 🌟 <br>— The Jemes Web Telescope Team</p></blockquote><p>Feel free to dive deeper into all our telescopic solutions!</p>'
        onUpdate={handleWordCount}
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

      <div className="flex justify-around w-full mt-4">
        <button
          className="bg-blue-500 text-white px-4 py-2 mx-2 rounded hover:bg-blue-600"
          onClick={previewData}
        >
          Preview
        </button>
        <button
          className="bg-blue-500 text-white px-4 py-2 mx-2 rounded hover:bg-blue-600"
          onClick={previewData}
        >
          Publish
        </button>
        <p>Word Count: {wordCount}</p>
      </div>

      {/* Preview */}
      <div className="mt-8 p-6 border border-gray-300 rounded-lg bg-gray-50">
        <h2 className="text-2xl font-bold mb-4">Blog Preview</h2>
        <div
          className="prose"
          dangerouslySetInnerHTML={{ __html: previewContent }}
        />
      </div>
    </div>
  );
};

export default EditBlog;
