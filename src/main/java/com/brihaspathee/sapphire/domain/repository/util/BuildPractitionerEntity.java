package com.brihaspathee.sapphire.domain.repository.util;

import com.brihaspathee.sapphire.domain.entity.Identifier;
import com.brihaspathee.sapphire.domain.entity.Practitioner;
import com.brihaspathee.sapphire.domain.entity.Qualification;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.Value;
import org.neo4j.driver.types.Node;

import java.util.List;
import java.util.Map;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 15, December 2025
 * Time: 05:44
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.domain.repository.util
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
public class BuildPractitionerEntity {

    public static Practitioner buildPractitioner(Value pracInfo){
        Node pracNode = pracInfo.get("prac").asNode();
        Practitioner practitioner = buildPractitioner(pracNode);
        Value pracDetails = pracInfo.get("pracDetails");
        List<Map<String, Object>> idList = pracDetails.get("identifiers").asList(Value::asMap);
        List<Identifier> identifiers = BuilderUtil.buildIdentifiers(idList);
        practitioner.setIdentifiers(identifiers);
        List<Node> qualNodes = pracDetails.get("qualifications").asList(Value::asNode);
        List<Qualification> qualifications = BuilderUtil.buildQualifications(qualNodes);
        practitioner.setQualifications(qualifications);
        return practitioner;
    }
    public static Practitioner buildPractitioner(Node practitionerNode){
        if (practitionerNode == null) {
            return null;
        }
        return Practitioner.builder()
                .elementId(practitionerNode.elementId())
                .code(practitionerNode.get("code").asString())
                .firstName(practitionerNode.get("firstName").asString())
                .lastName(practitionerNode.get("lastName").asString())
                .middleName(practitionerNode.get("middleName").asString(null))
                .gender(practitionerNode.get("gender").asString(null))
                .birthDate(practitionerNode.get("birthDate").asLocalDate(null))
                .altFirstName(practitionerNode.get("altFirstName").asString(null))
                .altLastName(practitionerNode.get("altLastName").asString(null))
                .altMiddleName(practitionerNode.get("altMiddleName").asString(null))
                .build();
    }
}
