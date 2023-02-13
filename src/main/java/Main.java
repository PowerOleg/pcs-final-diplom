import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
//List<PageEntry> list2 = bigList.stream().filter(n -> {                //что-то тут мало сравнений
//            boolean result = false;
//            for (PageEntry pageEntry3 : bigList) {
//                result = n.compare(pageEntry3);
//                System.out.println("1 " + n);
//                System.out.println("2 " + result);
//                if (n == pageEntry3) result = false;
//                System.out.println("3 " + result);
////                return result;
//            }
//            return result;
//        }).collect(Collectors.toList()));



//
//        List<PageEntry> list1 = bigList.stream().filter(n -> {                      //3
//                    for (PageEntry pageEntry3 : bigList) {
//                        return !n.compare(pageEntry3);                            //return если сробатывает то вылетает и
//                                                                                     //со следующим элементом действия - нет
//                }                                                             //проблема в двойном ретурне
public class Main {
//    public final ExecutorService executorService = Executors.newFixedThreadPool(4);
public static final String STOP_WORDS_FILE_NAME = "stop-ru.txt";
public static final String DIRECTORY_OF_PDFS = "pdfs";
    public static void main(String[] args) {
        BooleanSearchEngine engine = null;
        try {
            engine = new BooleanSearchEngine(new File(DIRECTORY_OF_PDFS), new File(STOP_WORDS_FILE_NAME));
        } catch (IOException e) {
            System.out.println("Выбран не верный файл со стоп словами");
            throw new RuntimeException(e);
        }


        MainServer mainServer = new MainServer(engine, new ServerLogic());
        mainServer.start();


















//        System.out.println(engine.search("объяснить концепцию"));


////         отвечать на запросы /{word} -> возвращённое значение метода search(word) в JSON-формате
////
//
//
//
//        List<PageEntry> bigList = new ArrayList<>(List.of(new PageEntry("a", 1, 2),
//                new PageEntry("a", 1, 10),
//                new PageEntry("b", 1, 3),
//                new PageEntry("a", 2, 1),
//                new PageEntry("a", 1, 4)));
//        System.out.println(bigList);
//
//        List<PageEntry> combinedList = new ArrayList<>();
//
//        List<PageEntry> removeList = new ArrayList<>();                                //4
////        for (PageEntry pageEntry1 : bigList) {
////            for (PageEntry pageEntry2 : bigList) {
////                if (pageEntry1 == pageEntry2) continue;
////                if (pageEntry1.compare(pageEntry2)) {
////                    removeList.add(pageEntry1);
////                    removeList.add(pageEntry2);
////                    combinationList.add(new PageEntry(pageEntry1.getPdfName(), pageEntry1.getPage(),
////                            (pageEntry1.getCount() + pageEntry2.getCount())));
////                }
////
////            }
////        }
////
////
////        persons
////                .stream()
////                .reduce((p1, p2) -> p1.age > p2.age ? p1 : p2)
////                .ifPresent(System.out::println);
//
//        Optional<PageEntry> list1 = bigList.stream().reduce((p1, p2) -> p1.compare(p2) ?
//                new PageEntry(p1.getPdfName(), p1.getPage(), p1.getCount()+ p2.getCount()) : null);
//        System.out.println("3 " + list1);
//
//        if (list1.isPresent()) {
//            System.out.println(list1.get());
//            combinedList.add(list1.get());
//        } else {
//            System.out.println(list1.get());
//        }
//
////        resultList.addAll(bigList);
////        combinedList = combinedList.stream().distinct().collect(Collectors.toList());
//        System.out.println("3 combinationList" + combinedList);
//
//        System.out.println("4 removeList " + removeList);
////        removeList = removeList.stream().distinct().collect(Collectors.toList());     //4
////        for (PageEntry p : removeList) {
////            resultList.remove(p);
////        }
//
////        bigList.removeAll(removeList);
//        System.out.println(bigList);
//
////        List<PageEntry> resultList = new ArrayList<>(bigList);
////        resultList.addAll(combinedList);
////        System.out.println("resultList " + resultList.stream()/*.distinct()*/.collect(Collectors.toList()));
////    }




    }

}