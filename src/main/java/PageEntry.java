public class PageEntry implements Comparable<PageEntry> {
    private final String pdfName;
    private final int page;
    private final int count;

    public PageEntry(String pdfName, int page, int count) {
        this.pdfName = pdfName;
        this.page = page;
        this.count = count;
    }

    @Override
    public int compareTo(PageEntry o) {
        return this.count - o.count;
    }

    @Override
    public String toString() {
        return "PageEntry{" +
                "pdf='" + pdfName + '\'' +
                ", page=" + page +
                ", count=" + count +
                '}';
    }
// списки ответов для каждого слова должны быть отсортированы в порядке уменьшения поля count.
    //    класс должен выдать:
//    PageEntry{pdf=Этапы оценки проекта_ понятия, методы и полезные инструменты.pdf, page=12, count=6}
}
