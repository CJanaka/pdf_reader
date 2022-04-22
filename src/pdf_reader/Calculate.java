package pdf_reader;

/**
 *
 * @author Jana
 */
public class Calculate {

    public int pagePerSheet(int pageCount, int pagePerSide, int numOfCopy, String sideCount, boolean pluz, boolean minuz) {
        int numOfSheet;
        int pagesPerOneSheet;

        if (numOfCopy != 0) {
            pageCount *= numOfCopy;
        }

        if (pagePerSide > 1) {
            numOfSheet = (pageCount / pagePerSide);
            if ((pageCount % pagePerSide) < pagePerSide && (pageCount % pagePerSide) != 0) {
                numOfSheet += 1;
            }
        } else {
            numOfSheet = pageCount;
        }

        if (sideCount.equals("Double Side")) {
            pagesPerOneSheet = (pagePerSide * 2);
            numOfSheet = pageCount / pagesPerOneSheet;
            if ((pageCount % pagesPerOneSheet) < pagesPerOneSheet && (pageCount % pagesPerOneSheet) != 0) {
                numOfSheet += 1;
            }
        }
        return numOfSheet;
    }
}
