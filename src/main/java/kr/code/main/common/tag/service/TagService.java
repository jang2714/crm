package kr.code.main.common.tag.service;

import kr.code.main.common.tag.domain.Tag;
import kr.code.main.common.tag.repository.TagsRepository;
import kr.code.main.customer.domain.CustomerVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagsRepository tagRepository;


    @Transactional
    public List<Tag> saveTagsFromCustomerInfo(CustomerVO customer) {
        // 입력 받은 고객정보를 태그로 변환하여 태그 엔티티를 리스트로 생성
        List<Tag> tags = analyzeCustomerInfo(customer);
        Set<Tag> uniqueTags = new HashSet<>(tags); // 중복 제거

        return tagRepository.saveAll(uniqueTags);
//        // 중복된 태그 처리 로직
//        List<Tag> existingTags = tagRepository.findAll();
//        List<String> tagTitleList = existingTags.stream()
//                .map(Tag::getTagTitle)
//                .collect(Collectors.toList());
//
//        List<Tag> toAddTags = uniqueTags.stream()
//                .filter(tag -> !tagTitleList.contains(tag.getTagTitle()))
//                .collect(Collectors.toList());
//        tagRepository.saveAll(toAddTags);
//
//        return toAddTags.stream().toList();
    }

    private List<Tag> analyzeCustomerInfo(CustomerVO customer) {
        List<Tag> tags = new ArrayList<>();

        // name tag
        tags.add(Tag.builder().title(customer.getCustomerName()).build());

        // email tag
        // 고객 이메일을 @로 분리하여 단어 단위로 배열에 저장
        Arrays.stream(customer.getCustomerEmail().split("@")).forEach( val -> {
            tags.add(Tag.builder().title(val).build());
        });

        // address tag
        // 고객 주소를 공백으로 분리하여 단어 단위로 배열에 저장
        Arrays.stream(customer.getAddress().split("\\s+")).forEach( val -> {
            val = val.replaceAll("[^a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ]", "");
            tags.add(Tag.builder().title(val).build());
        });

        // phone tag
        // 고객 전화번호를 공백으로 분리하여 단어 단위로 배열에 저장 (단, 010, 02, 031 등 지역번호는 제외)
        String[] numbers = customer.getPhoneNumber().split("-");
        if (numbers[1] != null && !numbers[1].isEmpty()) {
            tags.add(Tag.builder().title(numbers[1]).build());
        }
        if (numbers[2] != null && !numbers[2].isEmpty()) {
            tags.add(Tag.builder().title(numbers[2]).build());
        }

        // company tag
        tags.add(Tag.builder().title(customer.getCompanyName()).build());

        return tags;
    }

}
