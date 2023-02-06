import java.io.File;
import java.io.IOException;
import java.util.*;

public class BooleanSearchEngine implements SearchEngine {
    public  List<PageEntry> pageEntryList = new ArrayList<>(); //можно сделать var

    public Map<String, List<PageEntry>> resultList = new HashMap<>();             //можно сделать var

    //    Сканируя каждый пдф-файл вы перебираете его страницы, для каждой страницы извлекаете из неё слова и
//    подсчитываете их количество. После подсчёта, для каждого уникального слова пдф-файла создаёте объект PageEntry
//    и сохраняете в мапу в поле.

//    Для подсчёта частоты слов
//1 создаем список уникальных слов как указано ниже
//2     List<PageEntry>> =  список уникальных слов + еще что-то???














//    для каждого уникального слова pdf(у pdf своя мапа?)
//    Map<String, Integer> freqs = new HashMap<>(); // мапа, где ключом будет слово, а значением - частота
//for (var word : words) { // перебираем слова
//        if (word.isEmpty()) {
//            continue;
//        }
//        word = word.toLowerCase();
//        freqs.put(word, freqs.getOrDefault(word, 0) + 1);
//    }


//!Для каждой страницы нужен список слов и их количество Далее для каждого уникального слова пдф-файла создаёте объект PageEntry
//


//List<PageEntry> содержит список из номеров страниц pdf файла.
//    запрос слова бизнес выдает список где показано на какой !странице!, в каком pdf файле эта страница и
//    сколько раз встретилось данное слово
//[PageEntry{pdf=Этапы оценки проекта_ понятия, методы и полезные инструменты.pdf, page=12, count=6},
// PageEntry{pdf=Этапы оценки проекта_ понятия, методы и полезные инструменты.pdf, page=4, count=3},
// PageEntry{pdf=Этапы оценки проекта_ понятия, методы и полезные инструменты.pdf, page=5, count=3},
// PageEntry{pdf=1. DevOps_MLops.pdf, page=5, count=2},
// PageEntry{pdf=Что такое блокчейн.pdf, page=1, count=2},
// PageEntry{pdf=Что такое блокчейн.pdf, page=3, count=2}]

//это response сервера
//[
//    {
//        "pdfName": "Этапы оценки проекта_ понятия, методы и полезные инструменты.pdf",
//            "page": 12,
//            "count": 6
//    },
//    {
//        "pdfName": "Этапы оценки проекта_ понятия, методы и полезные инструменты.pdf",
//            "page": 4,
//            "count": 3
//    },
//    {
//        "pdfName": "Этапы оценки проекта_ понятия, методы и полезные инструменты.pdf",
//            "page": 5,
//            "count": 3
//    }
//]







    public BooleanSearchEngine(File pdfsDir) throws IOException {
        // прочтите тут все pdf и сохраните нужные данные,
        // тк во время поиска сервер не должен уже читать файлы
    }

    @Override
    public List<PageEntry> search(String word) {
        return resultList.computeIfAbsent(word, n -> null);
    }
}
