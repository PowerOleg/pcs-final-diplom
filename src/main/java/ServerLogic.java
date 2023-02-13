import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.stream.Collectors;

public class ServerLogic {
    private final Gson gson = new GsonBuilder().create();

    public String makeResponse(String clientRequest, BooleanSearchEngine booleanSearchEngine) {
        List<PageEntry> pageEntryList = booleanSearchEngine.search(clientRequest);
        return pageEntryList.stream().map(n -> convert(n)).collect(Collectors.joining(","));
    }

    public String convert(PageEntry pageEntry) {
        return gson.toJson(pageEntry);
    }
}
