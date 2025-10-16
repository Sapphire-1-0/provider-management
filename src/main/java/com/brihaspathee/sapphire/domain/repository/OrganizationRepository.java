package com.brihaspathee.sapphire.domain.repository;

import com.brihaspathee.sapphire.domain.entity.Organization;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 27, August 2025
 * Time: 05:18
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.domain.repository
 * To change this template use File | Settings | File and Code Template
 */
@Repository
public interface OrganizationRepository extends Neo4jRepository<Organization, String> {

    @Query("""
        MATCH (o:Organization)
        RETURN elementId(o) AS elementId,
               o.name AS name,
               o.aliasName AS aliasName,
               o.type AS type,
               o.atypical AS atypical,
               o.capitated AS capitated,
               o.pcpPractitionerRequired AS pcpPractitionerRequired
    """)
    List<Organization> findAllWithElementId();
}
