package org.main.unimap_pc.client.models;

import lombok.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Subject {
    private String code;
    private String name;
    private String type;
    private int credits;
    private String studyType;
    private String semester;
    private List<String> languages;
    private String completionType;
    private long studentCount;
    private List<EvaluationEntity> evaluation;
    private String assesmentMethods;
    private String learningOutcomes;
    private String courseContents;
    private String plannedActivities;
    private String evaluationMethods;
    private List<SubjectTeacherEntity> teacherList;
    public Subject(JSONObject jsonBase) {
        try {
            code = jsonBase.getString("code");
        } catch (org.json.JSONException e) {
            code = "";
        }
        try {
            name = jsonBase.getString("name");
        } catch (org.json.JSONException e) {
            name = "";
        }
        try {
            type = jsonBase.getString("type");
        } catch (org.json.JSONException e) {
            type = "";
        }
        try {
            credits = jsonBase.getInt("credits");
        } catch (org.json.JSONException e) {
            credits = -1;
        }
        try {
            studyType = jsonBase.getString("studyType");
        } catch (org.json.JSONException e) {
            studyType = "";
        }
        try {
            semester = jsonBase.getString("semester");
        } catch (org.json.JSONException e) {
            semester = "";
        }
        try {
            completionType = jsonBase.getString("completionType");
        } catch (org.json.JSONException e) {
            completionType = "";
        }
        try {
            studentCount = jsonBase.getInt("studentCount");
        } catch (org.json.JSONException e) {
            studentCount = -1;
        }
        try {
            assesmentMethods = jsonBase.getString("assesmentMethods");
        } catch (org.json.JSONException e) {
            assesmentMethods = "";
        }
        try {
            learningOutcomes = jsonBase.getString("learningOutcomes");
        } catch (org.json.JSONException e) {
            learningOutcomes = "";
        }
        try {
            courseContents = jsonBase.getString("courseContents");
        } catch (org.json.JSONException e) {
            courseContents = "";
        }
        try {
            languages = jsonBase.getJSONArray("languages").toList().stream()
                    .map(Object::toString)
                    .toList();
        } catch (org.json.JSONException e) {
            languages = new ArrayList<String>();
        }
        try {
            JSONArray evaluationArray = jsonBase.getJSONArray("evaluation");

            for (int i = 0; i < evaluationArray.length(); i++) {
                JSONObject evaluationObj = evaluationArray.getJSONObject(i);
                String grade = evaluationObj.keys().next();
                Double percentage = evaluationObj.getDouble(grade);
                evaluation.add(new EvaluationEntity(grade, percentage));
            }
        } catch (org.json.JSONException e) {
            evaluation = new ArrayList<EvaluationEntity>();
        }
        try {
            teacherList = jsonBase.getJSONArray("teachers").toList().stream().map(o -> {
                JSONObject teacherJson = new JSONObject((java.util.Map<?, ?>) o);
                Teacher teacher = null;
                List<String> roles = teacherJson.getJSONArray("roles").toList().stream().map(Object::toString).toList();
                List<String> languages = teacherJson.getJSONArray("languages").toList().stream().map(Object::toString).toList();
                return new SubjectTeacherEntity(teacher, roles, languages);
            }).toList();
        } catch (org.json.JSONException e) {
            teacherList = new ArrayList<SubjectTeacherEntity>();
        }
    }
}
