package com.example.lab23

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var searchEditText: EditText
    private lateinit var searchButton: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RepoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchEditText = findViewById(R.id.searchEditText)
        searchButton = findViewById(R.id.searchButton)
        recyclerView = findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)

        searchButton.setOnClickListener {
            val query = searchEditText.text.toString()
            searchRepositories(query)
        }
    }

    private fun searchRepositories(query: String) {
        val call = RetrofitClient.instance.searchRepositories(query)
        call.enqueue(object : Callback<RepositoryResponse> {
            override fun onResponse(
                call: Call<RepositoryResponse>,
                response: Response<RepositoryResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        adapter = RepoAdapter(it.items)
                        recyclerView.adapter = adapter
                    }
                }
            }

            override fun onFailure(call: Call<RepositoryResponse>, t: Throwable) {
                // Обработка ошибок
                t.printStackTrace()
            }
        })
    }
}
