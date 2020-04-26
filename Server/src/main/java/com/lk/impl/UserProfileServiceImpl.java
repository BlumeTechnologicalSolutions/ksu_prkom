package com.lk.impl;

import com.lk.entity.*;
import com.lk.service.UserProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named("userProfileService")
public class UserProfileServiceImpl implements UserProfileService {

    private static Logger logger = LoggerFactory.getLogger(UserProfileServiceImpl.class);

    @Override
    public Response getUserDocuments(HttpServletRequest request) {
        return null;
    }

    @Override
    public Response getUserEducation(HttpServletRequest request) {
        return null;
    }

    @Override
    public Response getUserRepresentative(HttpServletRequest request) {
        return null;
    }

    @Override
    public Response getUserAddress(HttpServletRequest request) {
        return null;
    }

    @Override
    public Response saveUserMainInfo(User user, HttpServletRequest request) {
        return null;
    }

    @Override
    public Response saveUserEducation(User user, HttpServletRequest request) {
        return null;
    }

    @Override
    public Response saveUserAddress(User user, HttpServletRequest request) {
        return null;
    }

    @Override
    public Response saveUserRepresentative(User user, HttpServletRequest request) {
        return null;
    }

    @Override
    public Response saveUserDocuments(User user, HttpServletRequest request) {
        return null;
    }


    usr_document_citizenship character varying(255),
    usr_document_type character varying(255),
    usr_document_series character varying(255),
    usr_document_number character varying(255),
    usr_document_code character varying(255),
    usr_document_receive_date date,
    usr_document_receive_by character varying(255),
    usr_document_place_of_birth character varying(255),

    usr_old_document_citizenship character varying(255),
    usr_old_document_type character varying(255),
    usr_old_document_series character varying(255),
    usr_old_document_number character varying(255),
    usr_old_document_code character varying(255),
    usr_old_document_receive_date date,
    usr_old_document_receive_by character varying(255),
    usr_old_document_place_of_birth character varying(255),

    usr_representative_last_name character varying(255),
    usr_representative_first_name  character varying(255),
    usr_representative_patronymic character varying(255),
    usr_representative_citizenship character varying(255),
    usr_representative_document_type character varying(255),
    usr_representative_series character varying(255),
    usr_representative_number character varying(255),
    usr_representative_document_code character varying(255),
    usr_representative_receiveDate date,
    usr_representative_receiveBy character varying(255),
    usr_representative_place_of_birth character varying(255),

    usr_address_country character varying(255),
    usr_address_region character varying(255),
    usr_address_locality_type character varying(255),
    usr_address_locality_name character varying(255),
    usr_address_street character varying(255),
    usr_address_house_number character varying(255),
    usr_address_room_number character varying(255),

    usr_education_documentType character varying(255),
    usr_education_series character varying(255),
    usr_education_number character varying(255),
    usr_education_receiveDate date,
    usr_education_receiveBy character varying(255),
    usr_education_finishDate date,
    usr_education_country character varying(255),
    usr_education_region character varying(255),
    usr_education_district character varying(255),

}
