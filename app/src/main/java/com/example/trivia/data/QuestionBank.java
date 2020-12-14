package com.example.trivia.data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.trivia.controller.AppController;
import com.example.trivia.model.Question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QuestionBank {
    ArrayList<Question> questionArrayList = new ArrayList<>();
    private String url = "https://opentdb.com/api.php?amount=20&category=17&difficulty=medium&type=boolean";

    public List<Question> getQuestions(final AnswerListAsyncResponse callback){

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d("JSON Stu", "onResponse: " + response.toString());

                        try {
                            JSONArray jsonArray = response.getJSONArray("results");
                            for(int i=0 ; i<jsonArray.length() ; i++){

                                Question question = new Question();

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                question.setAnswer(jsonObject.getString("question"));
                                question.setAnswerTrue(jsonObject.getBoolean("correct_answer"));

                                questionArrayList.add(question);
                                /*Log.d("JSON", "onResponse: " + jsonObject.getString("question")
                                + " " + jsonObject.getBoolean("correct_answer"));*/

                                //Log.d("JSON", "onResponse: " + question);
                                if(null != callback)
                                    callback.processFinished(questionArrayList);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);

        return questionArrayList;
    }

}
