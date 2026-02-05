package com.brihaspathee.sapphire.domain.repository.util;

import com.brihaspathee.sapphire.model.IdentifierDto;

import java.time.LocalDate;
import java.util.*;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 05, February 2026
 * Time: 05:42
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.domain.repository.util
 * To change this template use File | Settings | File and Code Template
 */
public class DataExtractor {

    public static Map<String, List<Map<String,Object>>> getIdentifiers(List<IdentifierDto> identifiers){
        Map<String, List<Map<String,Object>>> params = new HashMap<>();
        if(identifiers != null && !identifiers.isEmpty()){
            for (IdentifierDto identifier: identifiers){
                switch(identifier.getType()){
                    case "NPI":
                        Map<String, Object> npiMap = new HashMap<>();
                        npiMap.put("value", identifier.getValue());
                        npiMap.put("startDate", identifier.getStartDate());
                        npiMap.put("endDate",
                                Optional.ofNullable(identifier.getEndDate())
                                        .orElse(LocalDate.of(4000, 1, 1))
                        );
                        @SuppressWarnings("unchecked")
                        List<Map<String, Object>> npiList =
                                (List<Map<String, Object>>) params.computeIfAbsent(
                                        "npiList", k -> new ArrayList<>()
                                );

                        npiList.add(npiMap);
//                        params.put("npiList", List.of(
//                                Map.of("value", "1234567890", "startDate", LocalDate.of(2020,1,1)),
//                                Map.of("value", "9876543210") // second NPI optional dates
//                        ));
                        break;
                    case "MEDICARE_ID":
                        // Medicare identifiers
                        Map<String, Object> medicareIdMap = new HashMap<>();
                        medicareIdMap.put("value", identifier.getValue());
                        @SuppressWarnings("unchecked")
                        List<Map<String, Object>> medicareList =
                                (List<Map<String, Object>>) params.computeIfAbsent(
                                        "medicareList", k -> new ArrayList<>()
                                );

                        medicareList.add(medicareIdMap);
                        break;
//                        params.put("medicareList", List.of(
//                                Map.of("value", "MCR12345", "startDate", LocalDate.of(2022,6,1))
//                        ));
                    case "MEDICAID_ID":
                        // Medicaid identifiers (state-specific)
                        Map<String, Object> medicaidIdMap = new HashMap<>();
                        medicaidIdMap.put("value", identifier.getValue());
                        medicaidIdMap.put("state", identifier.getAdditionalProperties().get("state"));
                        medicaidIdMap.put("startDate", identifier.getStartDate());
                        medicaidIdMap.put("endDate",
                                Optional.ofNullable(identifier.getEndDate())
                                        .orElse(LocalDate.of(4000, 1, 1))
                        );
                        @SuppressWarnings("unchecked")
                        List<Map<String, Object>> medicaidList =
                                (List<Map<String, Object>>) params.computeIfAbsent(
                                        "medicaidList", k -> new ArrayList<>()
                                );

                        medicaidList.add(medicaidIdMap);
                        break;
//                        params.put("medicaidList", List.of(
//                                Map.of("value", "MD123", "state", "NY", "startDate", LocalDate.of(2021,1,1)),
//                                Map.of("value", "MD456", "state", "CA")
//                        ));

                }
            }

        }
        return params;
    }
}
