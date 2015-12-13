package kkmashup.ivr.routefinder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ozonetel.kookoo.TestClient;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.File;
import java.net.URL;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 *
 * @author Arun Kumar
 */
public class GetRouteDetails {

    private final String routeUrl = "http://www.bbus.in/api/v1/search/?";
    private OkHttpClient client;

    public GetRouteDetails() {
        client = new OkHttpClient();
    }

    public Routes findRoute(String source, String destination) {
        try {
            URL url = new URL(routeUrl + "from=" + source + "&to=" + destination + "&how=Minimum%20Number%20of%20Hops");

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            String jsonResponse = response.body().string();

            Gson gson = new GsonBuilder().create();
            Routes routes = gson.fromJson(jsonResponse, Routes.class);

            return routes;

        } catch (Exception ex) {
            Logger.getLogger(TestClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String check(String wordForSuggestions, ServletContext context) {
        try {

            if (wordForSuggestions == null || wordForSuggestions.isEmpty()) {
                return null;
            }
            if (wordForSuggestions.toLowerCase(Locale.getDefault()).equals("majestic")) {
                return "Kempegowda Bus Station/Majestic";
            }

            File dir = new File(context.getRealPath("spellchecker/"));

            Directory directory;
            directory = FSDirectory.open(dir);

            SpellChecker spellChecker = new SpellChecker(directory);

            spellChecker.indexDictionary(
                    new PlainTextDictionary(context.getResourceAsStream("/WEB-INF/json/bus_stop_list.txt")));

            int suggestionsNumber = 5;

            String[] suggestions = spellChecker.
                    suggestSimilar(wordForSuggestions, suggestionsNumber);

            if (suggestions != null && suggestions.length > 0) {
                /*for (String word : suggestions) {
                 System.out.println("Did you mean:" + word);
                 }*/
                //return Arrays.toString(suggestions);
                return suggestions[0];
            } else {
                //System.out.println("No suggestions found for word:"+wordForSuggestions);
            }
        } catch (Exception ex) {
            Logger.getLogger(TestClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }
}
