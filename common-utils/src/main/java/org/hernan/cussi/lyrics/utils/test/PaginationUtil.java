/*
* From https://vbmendes.medium.com/pagination-for-a-list-with-spring-pageable-26423455d0d3
*/

package org.hernan.cussi.lyrics.utils.test;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public final class PaginationUtil {

    public static <T> Page<T> paginateList(final Pageable pageable, List<T> list) {
        int first = Math.min(Long.valueOf(pageable.getOffset()).intValue(), list.size());;
        int last = Math.min(first + pageable.getPageSize(), list.size());
        return new PageImpl<>(list.subList(first, last), pageable, list.size());
    }
}
