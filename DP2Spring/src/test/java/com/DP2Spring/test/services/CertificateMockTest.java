package com.DP2Spring.test.services;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.DP2Spring.model.Certificate;
import com.DP2Spring.repository.CertificateRepository;
import com.DP2Spring.service.CertificateService;


@ExtendWith(MockitoExtension.class)
public class CertificateMockTest {

	@Mock
	private CertificateRepository certificateRepository;
	
	
	protected CertificateService certificateService;
	
	
	@BeforeEach
    void setup() {
		certificateService = new CertificateService(certificateRepository);
    }
	
	/*
	 * @Test
    void shouldFindVets() {
        Vet sampleVet = new Vet();
        sampleVet.setLastName("Douglas");
        Collection<Vet> sampleVets = new ArrayList<Vet>();
        sampleVets.add(sampleVet);
        when(vetRepository.findAll()).thenReturn(sampleVets);

        Collection<Vet> vets = this.vetService.findVets();

        assertThat(vets).hasSize(1);
        Vet vet = vets.iterator().next();
        assertThat(vet.getLastName()).isEqualTo("Douglas");
        assertThat(vet.getNrOfSpecialties()).isEqualTo(0);
    }
	 */
	
	void shouldFindCertificates() {
		Certificate cer = new Certificate();
		cer.setDescription("sample");
		cer.setEntity("sample");
		
		Collection<Certificate> sample = new ArrayList<Certificate>();
		
		sample.add(cer);
		
		when(certificateRepository.findAll()).thenReturn((List<Certificate>) sample);
	}

}
