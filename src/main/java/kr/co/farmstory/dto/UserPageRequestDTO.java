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
        // 정렬 조건이 없는 경우에는 정렬하지 않고 페이지네이션만 수행합니다.
        if (this.sort == null || this.sort.isEmpty()) {
            return PageRequest.of(this.pg - 1, this.size);
        } else {
            // 정렬 조건이 있는 경우에는 해당 조건으로 내림차순으로 정렬하여 페이지네이션합니다.
            return PageRequest.of(this.pg - 1, this.size, Sort.by(this.sort).descending());
        }
    }
}