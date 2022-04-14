package com.example.newsapp

import android.app.DownloadManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList
import org.json.JSONObject

class MainActivity : AppCompatActivity(), newsItemClicked {

    private lateinit var mAdapter:newsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

      recyclerView.layoutManager=LinearLayoutManager(this)
        fetchData()
        mAdapter= newsListAdapter(this)
        recyclerView.adapter=mAdapter


    }

    private fun fetchData() {
        val queue = Volley.newRequestQueue(this)
        val url="https://newsapi.org/v2/top-headlines?country=in&apiKey=2594b10be1e140f9bed395430d857a8f"
        val getRequest: JsonObjectRequest = object : JsonObjectRequest(
         Request.Method.GET,
            url,null,
            Response.Listener {
                val newsJsonArrays=it.getJSONArray("articles")
                val newsArray=ArrayList<News>()
                for(i in 0 until newsJsonArrays.length())
                {
                    val newsJsonObject=newsJsonArrays.getJSONObject(i)
                    val news=News(
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)
                }
                mAdapter.updateNews(newsArray)
            },
            Response.ErrorListener {

            }

        )
        //mySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
        {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["User-Agent"] = "Mozilla/5.0"
                return params
            }
        }
        queue.add(getRequest)

    }

    override fun onItemClicked(items: News) {

        val builder=  CustomTabsIntent.Builder();
        val  customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(items.url));
    }
}