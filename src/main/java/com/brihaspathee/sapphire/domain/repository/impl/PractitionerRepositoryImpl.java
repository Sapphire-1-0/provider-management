package com.brihaspathee.sapphire.domain.repository.impl;

import com.brihaspathee.sapphire.domain.entity.Practitioner;
import com.brihaspathee.sapphire.domain.repository.interfaces.PractitionerRepository;
import com.brihaspathee.sapphire.model.web.PractitionerSearchRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 11/18/25
 * Time: 7:12 PM
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.domain.repository.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class PractitionerRepositoryImpl implements PractitionerRepository {
    @Override
    public List<Practitioner> findPractitioners(PractitionerSearchRequest practitionerSearchRequest) {
        return List.of();
    }
}
