package com.example.skinsaathi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.example.skinsaathi.dto.ScanResponse;

@Service
public class SkinInsightService {

public ScanResponse buildResponse(Map<String, Object> aiResult) {

        String skinType = (String) aiResult.get("skin_type");
        String tipsText = (String) aiResult.get("tips_text");
        System.out.println("tipsText : "+tipsText);

        String insight = "";
        List<String> tips = new ArrayList<>();

        if (tipsText != null && !tipsText.isEmpty()) {

            String[] paragraphs = tipsText.split("\\n\\n+");

            insight = paragraphs[0].trim();

            for (int i = 1; i < paragraphs.length; i++) {
                String tip = paragraphs[i].trim();
                if (!tip.isEmpty()) {
                    tips.add(tip);
                }
            }
        }

        if (insight.isEmpty()) {
            insight = "Appearance-based skin analysis";
        }

        return new ScanResponse(
                skinType,
                insight,
                tips,
                "This is an appearance-based insight, not medical advice"
        );
    }
}
