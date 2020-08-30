package com.wr.util.file;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;

public class DesktopFileHandler
{
    public static void handle(Component parentComponent, String extension, boolean isSave, IFileHandler fileHandler)
    {
        JFileChooser fc = new JFileChooser();
        if (isSave)
        {
            fc.setApproveButtonText("Save");
        }
        else
        {
            fc.setApproveButtonText("Load");
        }
        FileFilter ff = new FileFilter()
        {
            @Override
            public boolean accept(File f)
            {
                if (f.isDirectory())
                {
                    return true;
                }

                return f.getName().endsWith("." + extension);
            }

            @Override
            public String getDescription()
            {
                return "*." + extension + " files";
            }
        };
        fc.addChoosableFileFilter(ff);
        fc.setFileFilter(ff);

        int returnVal = fc.showOpenDialog(parentComponent);
        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            File file = fc.getSelectedFile();
            if (isSave && !file.getAbsolutePath().endsWith(extension))
            {
                file = new File(file.getAbsolutePath() + "." + extension);
            }
            fileHandler.handleFile(file);
        }
    }

}
