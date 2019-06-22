package com.bookreader.zangke.bookreader;
import android.app.Activity;
import android.os.Environment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.arialyy.aria.core.Aria;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.folioreader.FolioReader;
import com.folioreader.model.HighLight;
import com.folioreader.ui.base.OnSaveHighlight;
import com.folioreader.util.OnHighlightListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements OnHighlightListener,FolioReader.OnClosedListener {
    private FolioReader folioReader;
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private Button start,openbook;
    private String DOWNLOAD_URL="http://bmob-cdn-2250.b0.upaiyun.com/2018/11/21/aefd267e400d1c8c8076ceed5d743ad2.epub";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        folioReader = FolioReader.get()
                .setOnHighlightListener(this)
//                .setReadLocatorListener(this)
                .setOnClosedListener(this);


        Aria.download(this).register();
        start = findViewById(R.id.start);
        final String filepath  = Environment.getExternalStorageDirectory().getPath()+"/bookeader/1.epub";
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Aria.download(this)
                        .load(DOWNLOAD_URL)
                        .setFilePath(filepath,true)
                        .start();
                Toast.makeText(MainActivity.this,"Downloading book",Toast.LENGTH_SHORT).show();
            }
        });
        final FolioReader folioReader = FolioReader.get();
        openbook =findViewById(R.id.openbook);
        openbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                folioReader.openBook("sdcard/Epub/h.epub");
//                folioReader.openBook(DOWNLOAD_URL)
                Toast.makeText(MainActivity.this,filepath,Toast.LENGTH_SHORT).show();
            }
        });

    }

    /*
     * For testing purpose, we are getting dummy highlights from asset. But you can get highlights from your server
     * On success, you can save highlights to FolioReader DB.
     */
    private void getHighlightsAndSave() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<HighLight> highlightList = null;
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    highlightList = objectMapper.readValue(
                            loadAssetTextAsString("highlights/highlights_data.json"),
                            new TypeReference<List<HighlightData>>() {
                            });
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (highlightList == null) {
                    folioReader.saveReceivedHighLights(highlightList, new OnSaveHighlight() {
                        @Override
                        public void onFinished() {
                            //You can do anything on successful saving highlight list
                        }
                    });
                }
            }
        }).start();
    }

    private String loadAssetTextAsString(String name) {
        BufferedReader in = null;
        try {
            StringBuilder buf = new StringBuilder();
            InputStream is = getAssets().open(name);
            in = new BufferedReader(new InputStreamReader(is));

            String str;
            boolean isFirst = true;
            while ((str = in.readLine()) != null) {
                if (isFirst)
                    isFirst = false;
                else
                    buf.append('\n');
                buf.append(str);
            }
            return buf.toString();
        } catch (IOException e) {
            Log.e("HomeActivity", "Error opening asset " + name);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    Log.e("HomeActivity", "Error closing asset " + name);
                }
            }
        }
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FolioReader.clear();
    }

    @Override
    public void onHighlight(HighLight highlight, HighLight.HighLightAction type) {
        Toast.makeText(this,
                "highlight id = " + highlight.getUUID() + " type = " + type,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFolioReaderClosed() {
        Log.v(LOG_TAG, "-> onFolioReaderClosed");
    }

}
