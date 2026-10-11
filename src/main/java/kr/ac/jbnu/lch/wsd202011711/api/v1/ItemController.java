package kr.ac.jbnu.lch.wsd202011711.api.v1;

import kr.ac.jbnu.lch.wsd202011711.api.dto.ItemDto;
import kr.ac.jbnu.lch.wsd202011711.api.dto.ResponseDto;
import kr.ac.jbnu.lch.wsd202011711.api.request.ItemCreateRequest;
import kr.ac.jbnu.lch.wsd202011711.api.request.ItemUpdateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/items")
public class ItemController {

    // 메모리용 임시 DB (예제용)
    private final Map<Long, ItemDto> store = new HashMap<>();
    private long sequence = 1L;

    // GET-1): 전체 조회
    @GetMapping
    public ResponseEntity<ResponseDto<List<ItemDto>>> getItems() {
        ResponseDto<List<ItemDto>> response = new ResponseDto<>("success", new  ArrayList<>(store.values()));
        return ResponseEntity.ok(response);
    }

    // GET-2): 단건 조회 (PathVariable 사용)
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<ItemDto>> getItem(@PathVariable Long id) {
        ItemDto item = store.get(id);
        if (item == null) {
            ResponseDto<ItemDto> response = new ResponseDto<>("failed", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        ResponseDto<ItemDto> response = new ResponseDto<>("success", item);
        return ResponseEntity.ok(response);
    }

    // POST-1): 생성
    @PostMapping
    public ResponseEntity<ResponseDto<ItemDto>> createItem(@RequestBody ItemCreateRequest request) {
        ItemDto item = new ItemDto();
        item.setId(sequence++);
        item.setName(request.getName());
        item.setPrice(request.getPrice());

        store.put(item.getId(), item);

        ResponseDto<ItemDto> response = new ResponseDto<>("success", item);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // POST-2): 무작위 생성
    @PostMapping("/randompost")
    public ResponseEntity<ResponseDto<ItemDto>> createRandomItem() {
        ItemDto item = new ItemDto();
        item.setId(sequence++);
        item.setName(String.valueOf(Math.random()));
        item.setPrice((int)(Math.random() * 10000)+1);

        store.put(item.getId(), item);

        ResponseDto<ItemDto> response = new ResponseDto<>("success", item);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // PUT-1): 수정
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<ItemDto>> updateItem(
            @PathVariable Long id,
            @RequestBody ItemUpdateRequest request
    ) {
        ItemDto item = store.get(id);
        if (item == null) {
            ResponseDto<ItemDto> response = new ResponseDto<>("failed", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        if (request.getName() != null) {
            item.setName(request.getName());
        }
        if (request.getPrice() != null) {
            item.setPrice(request.getPrice());
        }

        ResponseDto<ItemDto> response = new ResponseDto<>("success", item);
        return ResponseEntity.ok(response);
    }

    // PUT-2): 수정
    @PutMapping("/update")
    public ResponseEntity<ResponseDto<ItemDto>> discountItem(
            @RequestParam Long id,
            @RequestParam Long percentage
    ) {
        ItemDto item = store.get(id);
        if (item == null) {
            ResponseDto<ItemDto> response = new ResponseDto<>("failed", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        if (percentage <= 0 || percentage > 100) {
            ResponseDto<ItemDto> response = new ResponseDto<>("failed", item);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        item.setPrice((int)Math.floor(item.getPrice() * (100-percentage) / 100.0));

        ResponseDto<ItemDto> response = new ResponseDto<>("success", item);
        return ResponseEntity.ok(response);
    }

    // DELETE-1): 삭제 (Path Variable)
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteItem(@PathVariable Long id) {
        ItemDto removed = store.remove(id);
        if (removed == null) {
            ResponseDto<Void> response = new ResponseDto<>("failed", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        ResponseDto<Void> response = new ResponseDto<>("success", null);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    // DELETE-2): 삭제 (Request Parameter)
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto<Void>> deleteItemViaParameter(@RequestParam Long id) {
        ItemDto removed = store.remove(id);
        if (removed == null) {
            ResponseDto<Void> response = new ResponseDto<>("failed", null);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
        }
        ResponseDto<Void> response = new ResponseDto<>("success", null);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}
