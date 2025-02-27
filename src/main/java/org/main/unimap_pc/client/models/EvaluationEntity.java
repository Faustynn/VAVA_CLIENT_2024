package org.main.unimap_pc.client.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONObject;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EvaluationEntity {
    private String grade;
    private double percentage;

    public EvaluationEntity(JSONObject jsonBase) {
        try {
            this.grade = jsonBase.getString("name");
        } catch (org.json.JSONException e) {
            grade = "";
        }
        try {
            this.percentage = jsonBase.getDouble("percentage");
        }catch (org.json.JSONException e){
            percentage = -1;
        }
    }
}
