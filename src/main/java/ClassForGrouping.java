import java.util.Objects;

public class ClassForGrouping {
    private final String pdfName;
    private final int page;

    public ClassForGrouping(String pdfName, int page) {
        this.pdfName = pdfName;
        this.page = page;
    }

    public String getPdfName() {
        return pdfName;
    }

    public int getPage() {
        return page;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassForGrouping that = (ClassForGrouping) o;
        return page == that.page && Objects.equals(pdfName, that.pdfName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pdfName, page);
    }
}
