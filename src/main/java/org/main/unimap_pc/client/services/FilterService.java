package org.main.unimap_pc.client.services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.main.unimap_pc.client.models.Subject;
import org.main.unimap_pc.client.models.Teacher;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FilterService {

    public FilterService(){
        getSubjects();
        getTeachers();
    }

    private static String removeDiacritics(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .replace("\"", "")
                .replace("{", "")
                .replace("}", "");
    }

    private static JSONArray SubjectArray;
    private static JSONArray TeacherArray;

    private void getSubjects() {
        Object subjects = CacheService.get("SUBJECTS");
        System.out.println("HAAAAAA"+subjects);

        if (subjects instanceof String) {
            try {
                SubjectArray = new JSONObject((String) subjects).getJSONArray("subjects");
            } catch (JSONException e) {
                SubjectArray = new JSONArray();
            }
        } else if (subjects instanceof JSONArray) {
            SubjectArray = (JSONArray) subjects;
        } else {
            SubjectArray = new JSONArray();
        }
    }

    private void getTeachers() {
        Object teachers = CacheService.get("TEACHERS");
        System.out.println("HAAAAAA"+teachers);

        if (teachers instanceof String) {
            try {
                TeacherArray = new JSONObject((String) teachers).getJSONArray("teachers");
            } catch (JSONException e) {
                TeacherArray = new JSONArray();
            }
        } else if (teachers instanceof JSONArray) {
            TeacherArray = (JSONArray) teachers;
        } else {
            TeacherArray = new JSONArray();
        }
    }
    public static String subSearchForGarant(String subjectCode) {
        for (int i = 0; i < TeacherArray.length(); i++) {
            JSONObject teacher = TeacherArray.getJSONObject(i);
            JSONArray subjects = teacher.getJSONArray("subjects");
            for (int j = 0; j < subjects.length(); j++) {
                JSONObject subject = subjects.getJSONObject(j);
                if (subject.getString("subjectName").equals(subjectCode)) {
                    JSONArray roles = subject.getJSONArray("roles");
                    for (int k = 0; k < roles.length(); k++) {
                        String role = roles.getString(k);
                        if (removeDiacritics(role).equalsIgnoreCase("zodpovedny za predmet")) {
                            return teacher.getString("name");
                        }
                    }
                }
            }
        }
        return null; // or return an appropriate message if no guarantor is found
    }

    // FilterService.java
    public static List<Teacher> filterTeachers(teacherSearchForm searchForm) {
        List<Teacher> TeacherList = TeacherArray.toList().stream()
                .map(obj -> new JSONObject((Map<String, Object>) obj))
                .filter(searchForm.filterPredicate)
                .map(Teacher::new)
                .collect(Collectors.toList());
        return TeacherList;
    }

    public static List<Subject> filterSubjects(subjectSearchForm searchForm){
        List<Subject> SubjectList = SubjectArray.toList().stream()
                .map(obj -> new JSONObject((Map<String, Object>)  obj))
                .filter(searchForm.semesterPredicate)
                .filter(searchForm.studyTypePredicate)
                .filter(searchForm.subjectTypePredicate)
                .filter(searchForm.nameSearch)
                .map(jsonObject -> new Subject(jsonObject, new JSONObject().put("teachers", TeacherArray)))
                .collect(Collectors.toList());
        return SubjectList;
    }

    // FilterService.java
    public static class teacherSearchForm {
        private final Predicate<JSONObject> nameSearch;
        private final Predicate<JSONObject> teachesSubject;
        private final Predicate<JSONObject> hasTargetRole;
        private final Predicate<JSONObject> filterPredicate; //THIS ONE SHOULD BE USED
        public enum roleEnum{
            GARANT,
            CVICIACI,
            PREDNASAJUCI,
            SKUSAJUCI,
            NONE
        }

        public teacherSearchForm(String searchTerm, roleEnum targetRole, boolean isStrict) {
            teachesSubject = teacher -> {
                JSONArray subjects = teacher.getJSONArray("subjects");
                for (int i = 0; i < subjects.length(); i++) {
                    JSONObject subject = subjects.getJSONObject(i);
                    if (subject.getString("subjectName").toLowerCase().contains(removeDiacritics(searchTerm).toLowerCase())) {
                        if (targetRole != roleEnum.NONE) {
                            String targetRoleName = switch (targetRole) {
                                case GARANT -> "zodpovedny za predmet";
                                case CVICIACI -> "cviciaci";
                                case SKUSAJUCI -> "skusajuci";
                                case PREDNASAJUCI -> "prednasajuci";
                                default -> throw new IllegalStateException("Unexpected value: " + targetRole);
                            };
                            JSONArray roles = subject.getJSONArray("roles");
                            for (int j = 0; j < roles.length(); j++) {
                                String role = roles.getString(j);
                                System.out.println("CHECKING ROLE FOR " + teacher.getString("name") + ": " + role);
                                if (removeDiacritics(role).equalsIgnoreCase(targetRoleName)) {
                                    System.out.println("found a match");
                                    return true;
                                }
                            }
                            return false;
                        } else {
                            return true;
                        }
                    }
                }
                return false;
            };

            nameSearch = teacher -> {
                String originalName = teacher.getString("name");
                String normalizedName = removeDiacritics(originalName).toLowerCase();
                String normalizedSearchTerm = removeDiacritics(searchTerm).toLowerCase();
                return normalizedName.contains(normalizedSearchTerm);
            };

            // For strict mode when targetRole is specified
            hasTargetRole = teacher -> {
                JSONArray subjects = teacher.getJSONArray("subjects");
                String targetRoleName = switch (targetRole) {
                    case GARANT -> "zodpovedny za predmet";
                    case CVICIACI -> "cviciaci";
                    case SKUSAJUCI -> "skusajuci";
                    case PREDNASAJUCI -> "prednasajuci";
                    default -> "";
                };

                for (int i = 0; i < subjects.length(); i++) {
                    JSONObject subject = subjects.getJSONObject(i);
                    JSONArray roles = subject.getJSONArray("roles");
                    for (int j = 0; j < roles.length(); j++) {
                        String role = roles.getString(j);
                        if (removeDiacritics(role).equalsIgnoreCase(targetRoleName)) {
                            return true;
                        }
                    }
                }
                return false;
            };

            if (searchTerm.isBlank()) {
                filterPredicate = teachesSubject;
            } else {
                if (isStrict && targetRole != roleEnum.NONE) {
                    // For strict mode: include subject+role matches OR name matches that also have the target role
                    filterPredicate = teachesSubject.or(nameSearch.and(hasTargetRole));
                } else {
                    // For non-strict mode: use original logic
                    filterPredicate = teachesSubject.or(nameSearch);
                }
            }
        }
    }


    public static class subjectSearchForm{
        public enum subjectTypeEnum{POV,POV_VOL,VOL,NONE}
        public enum studyTypeEnum{BC,ING,NONE}
        public enum semesterEnum{ZS,LS,NONE}
        private final Predicate<JSONObject> nameSearch;
        private final Predicate<JSONObject> subjectTypePredicate;
        private final Predicate<JSONObject> studyTypePredicate;
        private final Predicate<JSONObject> semesterPredicate;
        public subjectSearchForm(String searchTerm, subjectTypeEnum subjectType, studyTypeEnum studyType,semesterEnum semester){
            nameSearch = subject -> {
                if(searchTerm.isBlank()){return true;}
                String originalName = subject.getString("name");
                String originalCode = subject.getString("code");
                String normalizedName = removeDiacritics(originalName).toLowerCase();
                String normalizedCode = removeDiacritics(originalCode).toLowerCase();
                String normalizedSearchTerm = removeDiacritics(searchTerm).toLowerCase();
                List<String> guarantors= filterTeachers(new teacherSearchForm(searchTerm, teacherSearchForm.roleEnum.GARANT,false))
                        .stream().map(Teacher::getName)
                        .filter(guarantor -> Objects.equals(subSearchForGarant(originalCode), guarantor))
                        .toList();
                System.out.println(guarantors);
                return normalizedName.contains(normalizedSearchTerm)
                        || normalizedCode.contains(normalizedSearchTerm)
                        || !guarantors.isEmpty();
            };
            subjectTypePredicate = subject ->{
                String type = subject.getString("type");
                type = type.toLowerCase();
                type = removeDiacritics(type);
                return switch (subjectType) {
                    case POV -> type.equals("povinny");
                    case POV_VOL -> type.equals("povinne volitelny");
                    case VOL -> type.equals("volitelny");
                    case NONE -> true;
                };
            };
            studyTypePredicate = subject ->{
                String type = subject.getString("studyType");
                type = type.toLowerCase();
                type = removeDiacritics(type);
                return switch (studyType) {
                    case BC -> type.equals("bakalarsky");
                    case ING -> type.equals("inziniersky");
                    case NONE -> true;
                };
            };
            semesterPredicate = subject ->{
                String type = subject.getString("semester");
                type = type.toUpperCase();
                type = removeDiacritics(type);
                return switch (semester) {
                    case ZS -> type.equals("ZS");
                    case LS -> type.equals("LS");
                    case NONE -> true;
                };
            };
        }
    }

}
