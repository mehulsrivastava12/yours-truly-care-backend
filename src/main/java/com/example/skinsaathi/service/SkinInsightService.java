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

            // Split AI text into sentences
            String[] parts = tipsText.split("\\. ");

            if (parts.length > 0) {
                insight = parts[0].trim();
            }

            // Take next 2–3 lines as tips
            for (int i = 1; i < parts.length && tips.size() < 3; i++) {
                tips.add(parts[i].trim());
            }
        }

        // Fallback safety (if AI fails)
        if (insight.isEmpty()) {
            insight = "Appearance-based skin analysis";
        }

        // if (tips.isEmpty()) {
        //     tips.add("Maintain a consistent skincare routine");
        //     tips.add("Use products suitable for your skin type");
        // }

        return new ScanResponse(
                skinType,
                insight,
                tips,
                "This is an appearance-based insight, not medical advice"
        );
    }
}
