package sample.cafekiosk.spring.domain;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.Modifying;

import java.time.LocalDateTime;

@MappedSuperclass   //하위 클래스들이 얘를 상속한다.
@Getter
@EntityListeners(AuditingEntityListener.class)  //AuditingEntityListener: Spring Data JPA에서 제공하는 이벤트 리스너로 엔티티의 영속, 수정 이벤트를 감지하는 역할
public abstract class BaseEntity {

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDateTime;

    @LastModifiedDate
    private LocalDateTime modifiedDateTime;

}
