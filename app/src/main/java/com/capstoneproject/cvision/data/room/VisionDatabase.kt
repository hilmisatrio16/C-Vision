package com.capstoneproject.cvision.data.room

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.capstoneproject.cvision.R
import com.capstoneproject.cvision.data.model.article.Article
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader

@Database(entities = [Article::class], version = 1, exportSchema = false)
abstract class VisionDatabase: RoomDatabase() {

    abstract fun articleDao(): ArticleDao

    companion object{
        @Volatile
        private var INSTANCE: VisionDatabase? = null
        fun getInstance(context: Context): VisionDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, VisionDatabase::class.java,
                    "vision_database"
                ).addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)

                        val daoArticle = getInstance(context).articleDao()
                        CoroutineScope(Dispatchers.IO).launch {
                            fillDataArticle(context, daoArticle)
                        }
                    }
                })
                    .build()
                INSTANCE = instance
                instance
            }



        }


        private fun fillDataArticle(context: Context, articleDao: ArticleDao) {
            val jsonArrayArticle = loadJsonArrayArticles(context)
            try {
                if (jsonArrayArticle != null) {
                    for (i in 0 until jsonArrayArticle.length()) {
                        val itemArticle = jsonArrayArticle.getJSONObject(i)
                        articleDao.insertArticles(
                            Article(
                                itemArticle.getInt("id"),
                                itemArticle.getString("title"),
                                itemArticle.getString("content"),
                                itemArticle.getString("urlImage"),
                                itemArticle.getString("urlArt")
                            )
                        )
                    }
                }


            } catch (e: Exception) {
                Log.e("ERROR", e.message.toString())
                e.printStackTrace()
            }
        }

        private fun loadJsonArrayArticles(context: Context): JSONArray? {
            try {
                val builder = StringBuilder()
                val `in` = context.resources.openRawResource(R.raw.articles)
                val readerBuffer = BufferedReader(InputStreamReader(`in`))
                var line: String?

                while (readerBuffer.readLine().also { line = it } != null) {

                    builder.append(line)
                }

                val jsonObject = JSONObject(builder.toString())
                return jsonObject.getJSONArray(("articles"))
            } catch (e: Exception) {
                Log.e("ERROR", e.message.toString())
                e.printStackTrace()
            }
            return null
        }
    }
}