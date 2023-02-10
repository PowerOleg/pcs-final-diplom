import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws Exception {
        long startTime = System.nanoTime();
        BooleanSearchEngine engine = new BooleanSearchEngine(new File("pdfs"));
        System.out.println(engine.search("работать с"));
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println(duration);
//        // здесь создайте сервер, который отвечал бы на нужные запросы
//        // слушать он должен порт 8989
//        // отвечать на запросы /{word} -> возвращённое значение метода search(word) в JSON-формате
//

//
//
//        List<PageEntry> bigList = new ArrayList<>(List.of(new PageEntry("a", 1, 2),
//                new PageEntry("b", 1, 3),
//                new PageEntry("a", 2, 1),
//                new PageEntry("a", 1, 4)));
//        System.out.println(bigList);
//
//        List<PageEntry> combinationList = new ArrayList<>();
//
//        List<PageEntry> removeList = new ArrayList<>();                                //4
//        for (PageEntry pageEntry1 : bigList) {
//            for (PageEntry pageEntry2 : bigList) {
//                if (pageEntry1 == pageEntry2) continue;
//                if (pageEntry1.compare(pageEntry2)) {
//                    removeList.add(pageEntry1);
//                    removeList.add(pageEntry2);
//                    combinationList.add(new PageEntry(pageEntry1.getPdfName(), pageEntry1.getPage(),
//                            (pageEntry1.getCount() + pageEntry2.getCount())));
//                }
//
//            }
//        }
////        removeList.addAll(bigList.stream().filter(n -> {                //что-то тут мало сравнений
////            boolean result = false;
////            for (PageEntry pageEntry3 : bigList) {
////                result = n.compare(pageEntry3);
////                System.out.println("1 " + n);
////                System.out.println("2 " + result);
////                if (n == pageEntry3) result = false;
////                System.out.println("3 " + result);
////                return result;
////            }
////            return result;
////        }).collect(Collectors.toList()));
////
////
////        List<PageEntry> list1 = bigList.stream().filter(n -> {                      //3
////                    for (PageEntry pageEntry3 : bigList) {
////                        return !n.compare(pageEntry3);
////                }
////                    return false;
////        }).collect(Collectors.toList());
////        System.out.println("3 " + list1);
//
////
////
//
////        resultList.addAll(bigList);
//        combinationList = combinationList.stream().distinct().collect(Collectors.toList());
//        System.out.println("3 combinationList" + combinationList);
//
//        System.out.println("4 removeList " + removeList);
////        removeList = removeList.stream().distinct().collect(Collectors.toList());     //4
////
////                                                                                                     //2
////
////        for (PageEntry p : removeList) {
////            resultList.remove(p);
////        }
//
//        bigList.removeAll(removeList);
//        System.out.println(bigList);
//
//        List<PageEntry> resultList = new ArrayList<>(bigList);
//        resultList.addAll(combinationList);
//        System.out.println("resultList " + resultList.stream()/*.distinct()*/.collect(Collectors.toList()));
////    }
////
////
//////    public boolean isWorkable(PageEntry pageEntry1, List<PageEntry> list1) {
//////        for (PageEntry pageEntry3 : list1) {
//////            return pageEntry1.compare(pageEntry3);
//////        }
//////          return false;
////
////

    }
}