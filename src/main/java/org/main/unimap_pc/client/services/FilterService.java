package org.main.unimap_pc.client.services;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FilterService {

    private static String removeDiacritics(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", ""); // Remove diacritical marks
    }

    private JSONArray SubjectArray;
    private JSONArray TeacherArray;

    private void getSubjects(){
        String subjectJson = "";
        SubjectArray = new JSONArray(subjectJson);
    }

    private void getTeachers(){
        String teacherJson = "";
        TeacherArray = new JSONArray(teacherJson);
    }


    public List<JSONObject> filterTeachers(teacherSearchForm searchForm){
        getTeachers();

        List<JSONObject> TeacherList = TeacherArray.toList().stream()
                .map(obj -> new JSONObject((String) obj))
                .filter(searchForm.teachesSubject)
                .filter(searchForm.nameSearch)
                .collect(Collectors.toList());
        return TeacherList;
    }

    public class teacherSearchForm{
        private final Predicate<JSONObject> nameSearch;
        private final Predicate<JSONObject> teachesSubject;
        public teacherSearchForm(String searchTerm,String targetSubject){
            teachesSubject = teacher -> {
                JSONArray subjects = teacher.getJSONArray("subject");
                for (int i = 0; i < subjects.length(); i++) {
                    JSONObject subject = subjects.getJSONObject(i);
                    if (subject.getString("name").equals(targetSubject)) {
                        return true;
                    }
                }
                return false;
            };

            nameSearch = teacher -> {
                String originalName = teacher.getString("name");
                String normalizedTeacherName = removeDiacritics(originalName).toLowerCase();
                String normalizedSearchTerm = removeDiacritics(searchTerm).toLowerCase();
                return normalizedTeacherName.contains(normalizedSearchTerm);
            };
        }

    }


}
