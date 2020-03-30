package com.DP2Spring.test.services;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
	
	
	
//	@Test
//	void shouldFindCertificates() {
//		Certificate cer = new Certificate();
//		cer.setDescription("sample");
//		cer.setEntity("sample");
//		
//		Collection<Certificate> sample = new ArrayList<Certificate>();
//		
//		sample.add(cer);
//		
//		when(certificateRepository.findAll()).thenReturn(sample);
//	}

}
