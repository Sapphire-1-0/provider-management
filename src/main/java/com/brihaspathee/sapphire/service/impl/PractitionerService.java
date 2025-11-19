package com.brihaspathee.sapphire.service.impl;

import com.brihaspathee.sapphire.domain.entity.Practitioner;
import com.brihaspathee.sapphire.domain.repository.interfaces.PractitionerRepository;
import com.brihaspathee.sapphire.model.PractitionerList;
import com.brihaspathee.sapphire.model.web.PractitionerSearchRequest;
import com.brihaspathee.sapphire.service.interfaces.IPractitionerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 11/18/25
 * Time: 7:08 PM
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.service.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PractitionerService implements IPractitionerService {

    private final PractitionerRepository practitionerRepository;

    @Override
    public PractitionerList getPractitioners(PractitionerSearchRequest practitionerSearchRequest) {
        List<Practitioner> practitioners = practitionerRepository.findPractitioners(practitionerSearchRequest);
        return null;
    }
}
