package org.softwaredev.springsecurity.common.application;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class MongoUtilService {

  // PageNo starts with 1 , not 0
  public Pageable getPageableObject(int pageNo, int pageSize, String sortBy) {
    return PageRequest.of(pageNo - 1, pageSize, Sort.by(sortBy).descending());
  }
}
