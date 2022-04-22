package pdf_reader;

import java.io.File;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Jana
 */
public class FolderDrager {

    public int folderItemCount(List<File> fie) {
        List<File> files = fie;
        int count = 0;
        System.out.println(files.size());
        for (File file : files) {
            if (file.isFile()) {
                JOptionPane.showMessageDialog(null, "You can drag only folder which contain images");
            } else {
                count = file.list().length;//return the file count of dragged folder
            }
        }
        return count;
    }
}
