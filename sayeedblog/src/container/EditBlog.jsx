import { Button } from "@mui/material";
import { Color } from "@tiptap/extension-color";
import FontFamily from "@tiptap/extension-font-family";
import Image from "@tiptap/extension-image";
import Strike from "@tiptap/extension-strike";
import Subscript from "@tiptap/extension-subscript";
import Superscript from "@tiptap/extension-superscript";
import Table from "@tiptap/extension-table";
import TableCell from "@tiptap/extension-table-cell";
import TableHeader from "@tiptap/extension-table-header";
import TableRow from "@tiptap/extension-table-row";
import TextAlign from "@tiptap/extension-text-align";
import Underline from "@tiptap/extension-underline";

import TextStyle from "@tiptap/extension-text-style";
import StarterKit from "@tiptap/starter-kit";

import {
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
  MenuSelectHeading,
  RichTextEditor,
} from "mui-tiptap";
import React from "react";

function insertImages(images) {
  if (!images || !Array.isArray(images)) {
    console.error("Expected an array of images but got:", images);
    return;
  }

  async function handleAndInsertNewFiles(files) {
    if (!files || !files.length) {
      console.error("No files or files array is empty");
      return;
    }
  }
}

const EditBlog = () => {
  return (
    <div className="p-10">
      <h1 className="text-4xl font-bold mb-4 text-center">BLOG EDITOR</h1>
      <RichTextEditor
        className="p-6 border border-gray-300 rounded-lg"
        extensions={[
          StarterKit,
          TextAlign.configure({
            types: ["heading", "paragraph"],
          }),
          MenuButtonUnderline,
          Table.configure({
            resizable: true,
          }),
          TableRow,
          TableCell,
          TableHeader,
          Strike,
          Subscript,
          Superscript,
          Color,
          Underline,
          TextStyle,
          Image,
          FontFamily,
        ]}
        content="<h1>Your Heading</h1><p>Your Summary</p><text>Your Blog</text>"
        renderControls={() => (
          <MenuControlsContainer>
            <MenuSelectHeading />
            <MenuDivider />
            <MenuSelectFontFamily
              options={[
                { label: "Default", value: "" },
                { label: "Arial", value: "arial" },
                { label: "Courier New", value: "courier-new" },
                { label: "Georgia", value: "georgia" },
                { label: "Times New Roman", value: "times-new-roman" },
                { label: "Verdana", value: "verdana" },
              ]}
            />
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
            <MenuButtonColorPicker />
            <MenuButtonAddTable />
            <MenuButtonEditLink />
            <MenuButtonStrikethrough />
            <MenuButtonSubscript />
            <MenuButtonSuperscript />
            <MenuButtonImageUpload
              onUploadFiles={(files) => {
                if (!files || !files.length) {
                  console.error("No files were uploaded");
                  return;
                }
                insertImages(files);
              }}
            />
            <MenuButtonRedo />
            <MenuButtonUndo />
          </MenuControlsContainer>
        )}
      />

      <Button className="mt-4">Log HTML</Button>
    </div>
  );
};

export default EditBlog;
