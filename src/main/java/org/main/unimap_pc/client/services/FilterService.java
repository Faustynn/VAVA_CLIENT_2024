package org.main.unimap_pc.client.services;

import org.json.JSONArray;
import org.json.JSONObject;
import org.main.unimap_pc.client.models.Subject;
import org.main.unimap_pc.client.models.Teacher;

import java.text.Normalizer;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FilterService {

    public FilterService(){
        getSubjects();
        getTeachers();
    }

    private static String removeDiacritics(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", ""); // Remove diacritical marks
    }

    private JSONArray SubjectArray;
    private JSONArray TeacherArray;

    private void getSubjects(){
        String subjectJson = "{\n" +
                "  \"subjects\": [\n" +
                "    {\n" +
                "      \"code\": \"VAVA_B\",\n" +
                "      \"name\": \"Vývoj aplikácií s viacvrstvovou architektúrou\",\n" +
                "      \"type\": \"povinne voliteľný\",\n" +
                "      \"credits\": 6,\n" +
                "      \"studyType\": \"bakalársky\",\n" +
                "      \"semester\": \"LS\",\n" +
                "      \"languages\": [\n" +
                "        \"{sk\",\n" +
                "        \"eng}\"\n" +
                "      ],\n" +
                "      \"completionType\": \"skúška\",\n" +
                "      \"studentCount\": 772,\n" +
                "      \"evaluation\": null,\n" +
                "      \"assesmentMethods\": \"70% formou samostatnej práce v podobe implementácie a jednoduchej dokumentácie prototypu postaveného na vybraných technológiách JAVA a DBMS\",\n" +
                "      \"learningOutcomes\": \"- Získať prehľad o platforme Java (Java Standard Edition), jej architektúre, štruktúre a vlastnostiach.\\\\n- Zdokonaliť sa vo vývoji programov pre platformu Java (Java Standard Edition).\\\\n- Nadobudnúť zručnosti vo využívaní vybraných rozhraní a rozširujúcich knižníc platformy Java (Java Standard Edition).\\\\n- Pripraviť sa na neskorší vývoj rozsiahlych viacvrstvových enterprise aplikácií.\\\\n- Používať jazyk ArchiMate a rámec TOGAF pri modelovaní viacvrstvových aplikácií.\\\\n- Zasadenie Java v kontexte JEE a .NET technológií.\",\n" +
                "      \"courseContents\": \"1. Štruktúra platformy Java\\\\n2. Java vývojové technológie a štandardy\\\\n3. Vybrané kapitoly/detaily Java Standard Edition API (napr. Collections, Logging, Localization, XML, I/O, Regular Expressions)\\\\n4. Databázy, JDBC, jazyk SQL v Jave\\\\n5. Prehľad JEE a .NET technológií a architektúr\",\n" +
                "      \"plannedActivities\": null,\n" +
                "      \"evaluationMethods\": \"Malé zadania - 10% z celkového hodnotenia predmetu.\\\\nSeminárna téma a prezentácia - 10% z celkového hodnotenia predmetu.\\\\nSemestrálny projekt - 40% z celkového hodnotenia predmetu.\\\\nSkúška - 40% z celkového hodnotenia predmetu.\",\n" +
                "      \"ascore\": \"30.8\",\n" +
                "      \"bscore\": \"22.9\",\n" +
                "      \"cscore\": \"19.2\",\n" +
                "      \"dscore\": \"14.9\",\n" +
                "      \"escore\": \"8.9\",\n" +
                "      \"fxscore\": \"3.3\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"SPRO_B\",\n" +
                "      \"name\": \"Špecifikačné prostriedky\",\n" +
                "      \"type\": \"povinne voliteľný\",\n" +
                "      \"credits\": 6,\n" +
                "      \"studyType\": \"bakalársky\",\n" +
                "      \"semester\": \"ZS\",\n" +
                "      \"languages\": [\n" +
                "        \"{sk\",\n" +
                "        \"eng}\"\n" +
                "      ],\n" +
                "      \"completionType\": \"skúška\",\n" +
                "      \"studentCount\": 213,\n" +
                "      \"evaluation\": null,\n" +
                "      \"assesmentMethods\": \"Pre predmet platia univerzitné a fakultné podmienky absolvovania a hodnotenia predmetov. Účasť na prednáškach a cvičeniach je povinná. Študent musí vypracovať všetky časti projektu podľa zadania a odovzdať ich v priebehu obdobia výučby. V opačnom prípade, študent bude hodnotený známkou FX.\\\\nNenulový počet bodov študent môže získať len za časti projektu odovzdané najneskôr v stanovených termínoch požadovaným spôsobom.\\\\nŠtudent, ktorý sa dopustí plagiátorstva v predmete v hocijakom rozsahu, bude hodnotený známkou FX.\\\\nV priebehu semestra je možné získať celkovo 60 bodov, konkrétne za:\\\\n- vypracovanie malých zadaní - 10 bodov (5-krát 2 body)\\\\n- seminárna téma a prezentácia, ktorej výsledkom je cvičiacim schválené zadanie semestrálneho projektu - 10 bodov\\\\n- semestrálny projekt - 40 bodov\\\\nTermíny:\\\\n- malé zadania - prvých 5 týždňov\\\\n- prezentácia seminárnej témy - 6. týždeň\\\\n- semestrálny projekt - 12. týždeň\\\\nPodmienkou na pripustenie ku skúške je získať minimálne 30 bodov z 60 a zároveň nemať žiadnu neospravedlnenú absenciu.\\\\nSkúška - 40 bodov.\\\\nNa úspešné ukončenie predmetu je potrebné získať minimálne 56 bodov z maximálneho bodového hodnotenia (zo 100 bodov), z toho minimálne 10 bodov za skúšku.\",\n" +
                "      \"learningOutcomes\": \"Predmet je venovaný základným prostriedkom pre formálnu špecifikáciu a modelovanie digitálnych systémov a ich komunikačných rozhraní. Podrobnejšie sa zaoberá štandardnými jazykmi RTL pre opis technických prostriedkov digitálnych systémov -- Verilog a SystemVerilog, špecifikačným nástrojom na opis správania rozličných počítačových, komunikačných a bezpečnostných systémov, opisom komunikačných rozhraní, verifikáciou RTL opisu digitálneho systému na základe špecifikácie požiadaviek a syntézou RTL opisu digitálneho systému do technológie hradlových polí (FPGA).\\\\nPredmet poskytuje základné špecifikačné prostriedky pre ďalšie predmety študijných programov Internetové technológie a Informačná Bezpečnosť. Poznatky získané z tohto predmetu sú vhodné nielen pre hardvérovo-zameraných študentov, ale aj pre zameranie počítačové a komunikačné siete a takziež pre zameranie počítačovú bezpečnosť.\\\\nÚspešné absolvovanie predmetu vyžaduje poznatky z logických systémov a základné poznatky z matematickej logiky.\",\n" +
                "      \"courseContents\": \"Digitálny systém, jeho modely, úrovne abstrakcie a špecifikácia požiadaviek\\\\nJazyk Verilog a jeho využitie\\\\nJazyk SystemVerilog a jeho využitie\\\\nSimulácia a verifikácia digitálnych systémov\\\\nProgramovateľné hradlové polia (FPGA) a syntéza do FPGA\\\\nKomunikačné rozhrania, siete, počítačová bezpečnosť a spôsoby ich opisu\",\n" +
                "      \"plannedActivities\": \"Predmet je rozdelený na prednášky a cvičenia. Prednášky sú realizované v štandardnom režime v priebehu semestra a pripravujú teoretickú bázu predmetu. Cvičenia sú venované najmä práci na jednoduchých zadaniach a semestrálnom projekte, kde si študenti prakticky precvičia návrh modelov správania a štruktúry digitálnych systémov a automatickú syntézu do FPGA obvodov.\",\n" +
                "      \"evaluationMethods\": \"Malé zadania - 10% z celkového hodnotenia predmetu.\\\\nSeminárna téma a prezentácia - 10% z celkového hodnotenia predmetu.\\\\nSemestrálny projekt - 40% z celkového hodnotenia predmetu.\\\\nSkúška - 40% z celkového hodnotenia predmetu.\",\n" +
                "      \"ascore\": \"5.6\",\n" +
                "      \"bscore\": \"21.1\",\n" +
                "      \"cscore\": \"32.9\",\n" +
                "      \"dscore\": \"22.1\",\n" +
                "      \"escore\": \"7.5\",\n" +
                "      \"fxscore\": \"10.8\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        SubjectArray = new JSONObject(subjectJson).getJSONArray("subjects");
    }

    private void getTeachers(){
        String teacherJson = "{\n" +
                "  \"teachers\": [\n" +
                "    {\n" +
                "      \"id\": \"1234\",\n" +
                "      \"name\": \"RNDr. Mgr. Ing. Miroslav Reiter, MBA\",\n" +
                "      \"email\": null,\n" +
                "      \"phone\": null,\n" +
                "      \"office\": null,\n" +
                "      \"subjects\": [\n" +
                "        {\n" +
                "          \"subjectName\": \"VAVA_B\",\n" +
                "          \"roles\": [\n" +
                "            \"{cvičiaci\",\n" +
                "            \"prednášajúci\",\n" +
                "            \"skúšajúci\",\n" +
                "            \"\\\"zodpovedný za predmet\\\"}\"\n" +
                "          ]\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"5813\",\n" +
                "      \"name\": \"doc. Ing. Lukáš Kohútka, PhD.\",\n" +
                "      \"email\": \"xkohutka@stuba.sk\",\n" +
                "      \"phone\": \"+421221022316\",\n" +
                "      \"office\": \"3.16\",\n" +
                "      \"subjects\": [\n" +
                "        {\n" +
                "          \"subjectName\": \"SPRO_B\",\n" +
                "          \"roles\": [\n" +
                "            \"{prednášajúci\",\n" +
                "            \"skúšajúci\",\n" +
                "            \"\\\"zodpovedný za predmet\\\"}\"\n" +
                "          ]\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"92443\",\n" +
                "      \"name\": \"RIng. Ján Mach\",\n" +
                "      \"email\": \"jan.mach@stuba.sk\",\n" +
                "      \"phone\": null,\n" +
                "      \"office\": null,\n" +
                "      \"subjects\": [\n" +
                "        {\n" +
                "          \"subjectName\": \"SPRO_B\",\n" +
                "          \"roles\": [\n" +
                "            \"{\\\"cvičiaci\\\"}\"\n" +
                "          ]\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        TeacherArray = new JSONObject(teacherJson).getJSONArray("teachers");
    }


    public List<Teacher> filterTeachers(teacherSearchForm searchForm){
        getTeachers();

        List<Teacher> TeacherList = TeacherArray.toList().stream()
                .map(obj -> new JSONObject((Map<String, Object>)  obj))
                .filter(searchForm.teachesSubject)
                .filter(searchForm.nameSearch)
                .map(Teacher::new)
                .collect(Collectors.toList());
        return TeacherList;
    }

    public List<Subject> filterSubjects(subjectSearchForm searchForm){
        getSubjects();

        List<Subject> SubjectList = SubjectArray.toList().stream()
                .map(obj -> new JSONObject((Map<String, Object>)  obj))
                .filter(searchForm.semesterPredicate)
                .filter(searchForm.studyTypePredicate)
                .filter(searchForm.subjectTypePredicate)
                .filter(searchForm.nameSearch)
                .map(Subject::new)
                .collect(Collectors.toList());
        return SubjectList;
    }

    public static class teacherSearchForm{
        private final Predicate<JSONObject> nameSearch;
        private final Predicate<JSONObject> teachesSubject;
        public teacherSearchForm(String searchTerm,String targetSubject){
            teachesSubject = teacher -> {
                if(targetSubject.isBlank()){
                    return true;
                }
                JSONArray subjects = teacher.getJSONArray("subjects");
                for (int i = 0; i < subjects.length(); i++) {
                    JSONObject subject = subjects.getJSONObject(i);
                    if (subject.getString("subjectName").equals(targetSubject)) {
                        return true;
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
                return normalizedName.contains(normalizedSearchTerm) || normalizedCode.contains(normalizedSearchTerm);
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
