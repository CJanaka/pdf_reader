package pdf_reader;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.TransferMode;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import org.controlsfx.control.textfield.CustomTextField;

/**
 *
 * @author Jana
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private AnchorPane dragPdf;
    @FXML
    private Text containBox;
    @FXML
    private ComboBox<Integer> pagePerSideBox;
    @FXML
    private ComboBox<String> sideCountBox;
    @FXML
    private CustomTextField pageCountBox;
    @FXML
    private ImageView img;
    @FXML
    private Text colorPages;
    @FXML
    private TextField noOfCopyBox;
    @FXML
    private AnchorPane dragFolder;
    @FXML
    private TextField changePgCount;
    @FXML
    private JFXRadioButton plus;
    @FXML
    private JFXRadioButton minus;
    @FXML
    private Text changeDetail;
    @FXML
    private ToggleGroup group;
    @FXML
    private JFXButton abtn;
    @FXML
    private JFXButton sbtn;

    int pageCount;
    int tempPageCount;
    List<File> files;
    int defaultPgCount;
    boolean pluz;
    boolean minuz;
    int newPageCount;
    boolean importBtnType = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sideCountBox.getItems().add("Double Side");
        sideCountBox.getItems().add("One Side");
        sideCountBox.getSelectionModel().selectFirst();
        pagePerSideBox.getItems().add(1);
        pagePerSideBox.getItems().add(2);
        pagePerSideBox.getItems().add(4);
        pagePerSideBox.getItems().add(6);
        pagePerSideBox.getItems().add(9);
        pagePerSideBox.getItems().add(16);
        pagePerSideBox.getSelectionModel().selectFirst();
        abtn.setVisible(false);
    }

    @FXML
    private void dragOver(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.ANY);
        }
    }

    @FXML
    private void dropEvent(DragEvent event) throws IOException {
        colorPages.setText("");
        files = event.getDragboard().getFiles();
        Container container = new Container();
        tempPageCount = container.docCount(files);
        pageCount = tempPageCount;
        containBox.setText("File Contain " + String.valueOf(pageCount) + " Pages");
        if (container.getColorPge() > 0) {
            colorPages.setText("File Contain " + String.valueOf(container.getColorPge()) + " Colour Pages");
        }
        if (sbtn.isVisible()) {
            changeDetail.setText("");
        }

    }

    @FXML
    private void dragEnter(DragEvent event) {
        dragPdf.setStyle(" -fx-opacity: 0.2");
    }

    @FXML
    private void dragExit(DragEvent event) {
        dragPdf.setStyle(" -fx-opacity: 1");
    }

    @FXML
    private void dragEnterS(DragEvent event) {
        dragFolder.setStyle(" -fx-opacity: 0.2");
    }

    @FXML
    private void dragExitS(DragEvent event) {
        dragFolder.setStyle(" -fx-opacity: 1");
    }

    @FXML
    private void mEnter() {
        noOfCopyBox.setTooltip(new Tooltip("If Copy Count Is more Than One, Enter The Copy Count Here"));
    }

    @FXML
    private void dragOv(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.ANY);
        }
    }

    @FXML
    private void dragDrop(DragEvent event) {
        colorPages.setText("");
        files = event.getDragboard().getFiles();
        FolderDrager fd = new FolderDrager();
        containBox.setText("This Folder Contain " + String.valueOf(fd.folderItemCount(files)) + " Files");
        changeDetail.setText("");
    }

    @FXML
    private void clic(ActionEvent event) {

        if (!sbtn.isVisible() && !importBtnType) {
            int a = JOptionPane.showConfirmDialog(null, "You didn't import the changed page count. Do you want to import it?");
            if (a == 0) {
                 importNewPages();
            }
        }
        int changeCount;
        int noOfCopy;
        try {
            int pagePerS = pagePerSideBox.getSelectionModel().getSelectedItem();
            String selectSide = sideCountBox.getSelectionModel().getSelectedItem();

            pluz = plus.isSelected();
            minuz = minus.isSelected();
            if (changePgCount.getText().equals("")) {
                changeCount = 0;
            } else {
                changeCount = Integer.parseInt(changePgCount.getText());
            }

            if (noOfCopyBox.getText().equals("")) {
                noOfCopy = 0;
            } else {
                noOfCopy = Integer.parseInt(noOfCopyBox.getText());
            }

            if (pluz) {
                pageCount += changeCount;
            } else if (minuz) {
                pageCount -= changeCount;
            }
            if (((pluz || minuz) && changeCount > 0) || ((!pluz || !minuz) && changeCount == 0)) {
                Calculate cal = new Calculate();
                int sCount = cal.pagePerSheet(pageCount, pagePerS, noOfCopy, selectSide, pluz, minuz);
                pageCountBox.setText("You want " + String.valueOf(sCount) + " Sheets");
            } else {
                JOptionPane.showMessageDialog(null, "Tick the one of button and Enter the changing count");
            }

        } catch (NumberFormatException e) {
            System.out.println(e.getMessage() + e);
        } finally {
            clear();
        }
    }

    @FXML
    private void released(KeyEvent event) {
        if (plus.isSelected()) {
            changeDetail.setText("You changed page ount as " + String.valueOf(pageCount + (Integer.parseInt(changePgCount.getText()))) + " pages");
        } else if (minus.isSelected()) {
            changeDetail.setText("You changed page ount as " + String.valueOf(pageCount - (Integer.parseInt(changePgCount.getText()))) + " pages");
        }
        PauseTransition visiblePause = new PauseTransition(Duration.seconds(10));
        visiblePause.setOnFinished(eve -> changeDetail.setText(""));
        visiblePause.play();
    }

    public void clear() {
        plus.setSelected(false);
        minus.setSelected(false);
        changePgCount.setText("");
        noOfCopyBox.setText("");
        sideCountBox.getSelectionModel().selectFirst();
        pagePerSideBox.getSelectionModel().selectFirst();
        pageCount = tempPageCount;
        changeDetail.setText("");
        abtn.setVisible(false);
        sbtn.setVisible(true);
    }

    @FXML
    private void mEnterChangeCount(MouseEvent event) {
        changePgCount.setTooltip(new Tooltip("You can change page count in here"));
    }

    @FXML
    private void mExit1(MouseEvent event) {
        dragPdf.setStyle(" -fx-opacity: 1");
    }

    @FXML
    private void mExit2(MouseEvent event) {
        dragFolder.setStyle(" -fx-opacity: 1");
    }

    @FXML
    private void mclick(MouseEvent event) {
        if (pageCount > 0) {
            newPageCount = pageCount;
            changeDetail.setText("Page count saved, " + newPageCount + " pages");
            sbtn.setVisible(false);
            abtn.setVisible(true);
        }
    }

    @FXML
    private void add(MouseEvent event) {
        newPageCount += pageCount;
        changeDetail.setText("Page count is  " + newPageCount + " pages");
    }

    @FXML
    private void imprt(MouseEvent event) {
        importNewPages();
    }

    public void importNewPages() {

        importBtnType = true;
        pageCount = newPageCount;
        JOptionPane.showMessageDialog(null, "New page count imported !");
        changeDetail.setText("New page count is  " + newPageCount + " pages");
    }
}
