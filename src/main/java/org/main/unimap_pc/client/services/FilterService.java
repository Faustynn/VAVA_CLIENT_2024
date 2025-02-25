package org.main.unimap_pc.client.services;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FilterService {

    public FilterService(){}

    private static String removeDiacritics(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", ""); // Remove diacritical marks
    }

    private JSONArray SubjectArray;
    private JSONArray TeacherArray;

    private void getSubjects(){
        String subjectJson = "[\n" +
                "    {\n" +
                "        \"name\": \"Vývoj aplikácií s viacvrstvovou architektúrou\",\n" +
                "        \"code\": \"VAVA_B\",\n" +
                "        \"type\": \"povinne voliteľný\",\n" +
                "        \"credits\": 6,\n" +
                "        \"studyType\": \"bakalársky\",\n" +
                "        \"semester\": \"LS\",\n" +
                "        \"languages\": [\"sk\", \"eng\"],\n" +
                "        \"completionType\": \"skúška\",\n" +
                "        \"studentCount\": 772,\n" +
                "        \"evaluation\": {\n" +
                "            \"A\": 30.8,\n" +
                "            \"B\": 22.9,\n" +
                "            \"C\": 19.2,\n" +
                "            \"D\": 14.9,\n" +
                "            \"E\": 8.9,\n" +
                "            \"Fx\": 3.3\n" +
                "        },\n" +
                "        \"assesmentMethods\": \"70% formou samostatnej práce v podobe implementácie a jednoduchej dokumentácie prototypu postaveného na vybraných technológiách JAVA a DBMS\",\n" +
                "        \"learningOutcomes\": \"- Získať prehľad o platforme Java (Java Standard Edition), jej architektúre, štruktúre a vlastnostiach.\\n- Zdokonaliť sa vo vývoji programov pre platformu Java (Java Standard Edition).\\n- Nadobudnúť zručnosti vo využívaní vybraných rozhraní a rozširujúcich knižníc platformy Java (Java Standard Edition).\\n- Pripraviť sa na neskorší vývoj rozsiahlych viacvrstvových enterprise aplikácií.\\n- Používať jazyk ArchiMate a rámec TOGAF pri modelovaní viacvrstvových aplikácií.\\n- Zasadenie Java v kontexte JEE a .NET technológií.\",\n" +
                "        \"courseContents\": \"1. Štruktúra platformy Java\\n2. Java vývojové technológie a štandardy\\n3. Vybrané kapitoly/detaily Java Standard Edition API (napr. Collections, Logging, Localization, XML, I/O, Regular Expressions)\\n4. Databázy, JDBC, jazyk SQL v Jave\\n5. Prehľad JEE a .NET technológií a architektúr\",\n" +
                "        \"teachers\": [\n" +
                "            {\n" +
                "                \"name\": \"RNDr. Mgr. Ing. Miroslav Reiter, MBA\",\n" +
                "                \"roles\": [\"cvičiaci\", \"prednášajúci\", \"skúšajúci\", \"zodpovedný za predmet\"],\n" +
                "                \"languages\": []\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "    {\n" +
                "        \"code\": \"SPRO_B\",\n" +
                "        \"name\": \"Špecifikačné prostriedky\",\n" +
                "        \"type\": \"povinne voliteľný\",\n" +
                "        \"credits\": 6,\n" +
                "        \"studyType\": \"bakalársky\",\n" +
                "        \"semester\": \"ZS\",\n" +
                "        \"languages\": [\"sk\", \"eng\"],\n" +
                "        \"completionType\": \"skúška\",\n" +
                "        \"studentCount\": 213,\n" +
                "        \"evaluation\": {\n" +
                "            \"A\": 5.6,\n" +
                "            \"B\": 21.1,\n" +
                "            \"C\": 32.9,\n" +
                "            \"D\": 22.1,\n" +
                "            \"E\": 7.5,\n" +
                "            \"Fx\": 10.8\n" +
                "        },\n" +
                "        \"assesmentMethods\": \"Pre predmet platia univerzitné a fakultné podmienky absolvovania a hodnotenia predmetov. Účasť na prednáškach a cvičeniach je povinná. Študent musí vypracovať všetky časti projektu podľa zadania a odovzdať ich v priebehu obdobia výučby. V opačnom prípade, študent bude hodnotený známkou FX.\\nNenulový počet bodov študent môže získať len za časti projektu odovzdané najneskôr v stanovených termínoch požadovaným spôsobom.\\nŠtudent, ktorý sa dopustí plagiátorstva v predmete v hocijakom rozsahu, bude hodnotený známkou FX.\\nV priebehu semestra je možné získať celkovo 60 bodov, konkrétne za:\\n- vypracovanie malých zadaní - 10 bodov (5-krát 2 body)\\n- seminárna téma a prezentácia, ktorej výsledkom je cvičiacim schválené zadanie semestrálneho projektu - 10 bodov\\n- semestrálny projekt - 40 bodov\\nTermíny:\\n- malé zadania - prvých 5 týždňov\\n- prezentácia seminárnej témy - 6. týždeň\\n- semestrálny projekt - 12. týždeň\\nPodmienkou na pripustenie ku skúške je získať minimálne 30 bodov z 60 a zároveň nemať žiadnu neospravedlnenú absenciu.\\nSkúška - 40 bodov.\\nNa úspešné ukončenie predmetu je potrebné získať minimálne 56 bodov z maximálneho bodového hodnotenia (zo 100 bodov), z toho minimálne 10 bodov za skúšku.\",\n" +
                "        \"learningOutcomes\": \"Predmet je venovaný základným prostriedkom pre formálnu špecifikáciu a modelovanie digitálnych systémov a ich komunikačných rozhraní. Podrobnejšie sa zaoberá štandardnými jazykmi RTL pre opis technických prostriedkov digitálnych systémov -- Verilog a SystemVerilog, špecifikačným nástrojom na opis správania rozličných počítačových, komunikačných a bezpečnostných systémov, opisom komunikačných rozhraní, verifikáciou RTL opisu digitálneho systému na základe špecifikácie požiadaviek a syntézou RTL opisu digitálneho systému do technológie hradlových polí (FPGA).\\nPredmet poskytuje základné špecifikačné prostriedky pre ďalšie predmety študijných programov Internetové technológie a Informačná Bezpečnosť. Poznatky získané z tohto predmetu sú vhodné nielen pre hardvérovo-zameraných študentov, ale aj pre zameranie počítačové a komunikačné siete a takziež pre zameranie počítačovú bezpečnosť.\\nÚspešné absolvovanie predmetu vyžaduje poznatky z logických systémov a základné poznatky z matematickej logiky.\",\n" +
                "        \"courseContents\": \"Digitálny systém, jeho modely, úrovne abstrakcie a špecifikácia požiadaviek\\nJazyk Verilog a jeho využitie\\nJazyk SystemVerilog a jeho využitie\\nSimulácia a verifikácia digitálnych systémov\\nProgramovateľné hradlové polia (FPGA) a syntéza do FPGA\\nKomunikačné rozhrania, siete, počítačová bezpečnosť a spôsoby ich opisu\",\n" +
                "        \"plannedActivities\": \"Predmet je rozdelený na prednášky a cvičenia. Prednášky sú realizované v štandardnom režime v priebehu semestra a pripravujú teoretickú bázu predmetu. Cvičenia sú venované najmä práci na jednoduchých zadaniach a semestrálnom projekte, kde si študenti prakticky precvičia návrh modelov správania a štruktúry digitálnych systémov a automatickú syntézu do FPGA obvodov.\",\n" +
                "        \"evaluationMethods\": \"Malé zadania - 10% z celkového hodnotenia predmetu.\\nSeminárna téma a prezentácia - 10% z celkového hodnotenia predmetu.\\nSemestrálny projekt - 40% z celkového hodnotenia predmetu.\\nSkúška - 40% z celkového hodnotenia predmetu.\",\n" +
                "        \"teachers\": [\n" +
                "            {\n" +
                "                \"name\": \"doc. Ing. Lukáš Kohútka, PhD.\",\n" +
                "                \"roles\": [\"prednášajúci\", \"skúšajúci\", \"zodpovedný za predmet\"],\n" +
                "                \"languages\": [\"slovenský jazyk\"]\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"RIng. Ján Mach\",\n" +
                "                \"roles\": [\"cvičiaci\"],\n" +
                "                \"languages\": [\"slovenský jazyk\"]\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "]";
        SubjectArray = new JSONArray(subjectJson);
    }

    private void getTeachers(){
        String teacherJson = "[\n" +
                "    {\n" +
                "        \"id\": 92443,\n" +
                "        \"name\": \"doc. Ing. Lukáš Kohútka, PhD.\",\n" +
                "        \"email\": \"jan.mach@stuba.sk\",\n" +
                "        \"number\": null,\n" +
                "        \"personalOffice\": null,\n" +
                "        \"subject\": [\n" +
                "            {\n" +
                "                \"name\": \"SPRO_B\",\n" +
                "                \"roles\": [\"cvičiaci\"]\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"VNOS_I\",\n" +
                "                \"roles\": [\"cvičiaci\"]\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"DSA_B\",\n" +
                "                \"roles\": [\"prednasajuci\"]\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 92443,\n" +
                "        \"name\": \"Ing. Ján Mach\",\n" +
                "        \"email\": \"jan.mach@stuba.sk\",\n" +
                "        \"number\": null,\n" +
                "        \"personalOffice\": null,\n" +
                "        \"subject\": [\n" +
                "            {\n" +
                "                \"name\": \"SPRO_B\",\n" +
                "                \"roles\": [\"cvičiaci\"]\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"VNOS_I\",\n" +
                "                \"roles\": [\"cvičiaci\"]\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "]";
        TeacherArray = new JSONArray(teacherJson);
    }


    public List<String> filterTeachers(teacherSearchForm searchForm){
        getTeachers();

        List<String> TeacherList = TeacherArray.toList().stream()
                .map(obj -> new JSONObject((Map<String, Object>)  obj))
                .filter(searchForm.teachesSubject)
                .filter(searchForm.nameSearch)
                .map(teacher -> teacher.getString("name")) // Extract the name of the teacher
                .collect(Collectors.toList());
        return TeacherList;
    }

    public List<String> filterSubjects(subjectSearchForm searchForm){
        getSubjects();

        List<String> SubjectList = SubjectArray.toList().stream()
                .map(obj -> new JSONObject((Map<String, Object>)  obj))
                .filter(searchForm.semesterPredicate)
                .filter(searchForm.studyTypePredicate)
                .filter(searchForm.subjectTypePredicate)
                .filter(searchForm.nameSearch)
                .map(teacher -> teacher.getString("code")+" "+teacher.getString("name"))
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
