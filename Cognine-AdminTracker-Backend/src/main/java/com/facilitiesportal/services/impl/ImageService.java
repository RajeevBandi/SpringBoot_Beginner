package com.facilitiesportal.services.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.facilitiesportal.dao.VisitorRepo;
import com.facilitiesportal.dao.Visitor_IdProofImageRepo;
import com.facilitiesportal.model.Image;
import com.facilitiesportal.model.Visitor;
import com.facilitiesportal.services.IImageService;
import com.facilitiesportal.util.ImageConverterUtil;

/**
 * service class to manage operations related to visitor images.
 */
@Service
public class ImageService implements IImageService {

	@Autowired
	private VisitorRepo visitorRepo;

	@Autowired
	private Visitor_IdProofImageRepo visitor_IdProofImageRepo;

	/**
     * Retrieves the profile image of a visitor.
     *
     * @param visitorId The ID of the visitor.
     * @return A map containing the profile image base64 string.
     */
	@Override
	public Map<String, Object> getVisitorProfileImage(long visitorId) {
		Map<String, Object> response = new HashMap<>();
		try {
			Visitor visitor = this.visitorRepo.findById(visitorId);
			if (visitor.getProfileImageId() != null) {
				String profileImageBase64 = ImageConverterUtil
						.byteArrayToBase64(visitor.getProfileImageId().getEncodedImage());
				response.put("profileImageBase64", profileImageBase64);
			} else {
				response.put("profileImageBase64", null);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return response;
	}

	/**
     * Retrieves all ID proof images associated with a visitor.
     *
     * @param visitorId The ID of the visitor.
     * @return A map containing the ID proof images base64 strings.
     */
	@Override
	public Map<String, Object> getVisitorIdProofImages(long visitorId){
			Map<String, Object> response = new LinkedHashMap<>();
			try {
				List<Image> visitorsIdProofImages = visitor_IdProofImageRepo.findImagesByVisitorId(visitorId);
				
				if(visitorsIdProofImages!=null && visitorsIdProofImages.size()>0)
				{
					for (int i = 0; i < visitorsIdProofImages.size(); i++) {
					    Image idProofImage = visitorsIdProofImages.get(i);
					    String idProofImageBase64 = ImageConverterUtil.byteArrayToBase64(idProofImage.getEncodedImage());
					    response.put("idProofImage" +( i+1) + "_Base64", idProofImageBase64);
					}
				}				
			}
			catch (Exception e) {
				System.out.println(e);
			}
		return response;
		}
}
