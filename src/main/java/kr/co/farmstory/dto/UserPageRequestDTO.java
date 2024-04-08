package kr.co.farmstory.dto;

import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserPageRequestDTO {

    @Builder.Default
    private String sort = null;

    @Builder.Default
    private int pg=1;

    @Builder.Default
    private int size =10;

    public Pageable getPageable() {
        Sort.Direction direction = Sort.Direction.DESC; // 기본 정렬 방향은 내림차순

        if (this.sort != null && this.sort.toLowerCase().endsWith("-asc")) {
            direction = Sort.Direction.ASC; // 클라이언트 요청이 asc이면 오름차순으로 변경
            this.sort = this.sort.substring(0, this.sort.length() - 4); // 정렬 방식 부분 제거
        } else if (this.sort != null && this.sort.toLowerCase().endsWith("-desc")) {
            this.sort = this.sort.substring(0, this.sort.length() - 5); // 정렬 방식 부분 제거
        }

        // 기본 정렬 기준 설정
        if (this.sort == null) {
            // sort가 null인 경우 기본값으로 regDate를 내림차순으로 설정
            Sort sortCriteria = Sort.by(Sort.Direction.DESC, "regDate");
            return PageRequest.of(this.pg - 1, this.size, sortCriteria);
        }

        // 정렬 기준 설정
        Sort sortCriteria;
        switch (this.sort) {
            case "uid":
                sortCriteria = Sort.by(direction, "uid");
                break;
            case "name":
                sortCriteria = Sort.by(direction, "name");
                break;
            case "nick":
                sortCriteria = Sort.by(direction, "nick");
                break;
            case "email":
                sortCriteria = Sort.by(direction, "email");
                break;
            case "grade":
                sortCriteria = Sort.by(direction, "grade");
                break;
            case "regDate":
            default:
                sortCriteria = Sort.by(direction, "regDate");
                break;
        }

        // 페이지네이션 설정
        return PageRequest.of(this.pg - 1, this.size, sortCriteria);
    }

}