package com.app.springpowpow.controller;

import com.app.springpowpow.domain.PostDTO;
import com.app.springpowpow.domain.PostVO;
import com.app.springpowpow.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/*")
public class PostAPI {

    private final PostService postService;

    //    게시글 전체 조회
    @Operation(summary = "게시글 정보 조회", description = "게시글 정보를 전체 조회할 수 있는 API")
    @Parameters({
            @Parameter( name = "id", description = "게시글 번호", schema = @Schema(type="number"), in = ParameterIn.HEADER, required = true ),
            @Parameter( name = "postContent", description = "게시글 내용", schema = @Schema(type="string"), in = ParameterIn.HEADER, required = true ),
            @Parameter( name = "postImage1", description = "게시글 이미지1", schema = @Schema(type="string"), in = ParameterIn.HEADER, required = true ),
            @Parameter( name = "postImage2", description = "게시글 이미지2", schema = @Schema(type="string"), in = ParameterIn.HEADER ),
            @Parameter( name = "postImage3", description = "게시글 이미지3", schema = @Schema(type="string"), in = ParameterIn.HEADER),
            @Parameter( name = "postImage4", description = "게시글 이미지4", schema = @Schema(type="string"), in = ParameterIn.HEADER),
            @Parameter( name = "postImage5", description = "게시글 이미지5", schema = @Schema(type="string"), in = ParameterIn.HEADER),
            @Parameter( name = "memberId", description = "회원 번호", schema = @Schema(type="number"), in = ParameterIn.HEADER, required = true ),
            @Parameter( name = "memberEmail", description = "회원 이메일", schema = @Schema(type="string"), in = ParameterIn.HEADER ),
            @Parameter( name = "memberName", description = "회원 이름", schema = @Schema(type="string"), in = ParameterIn.HEADER )
    })
    @GetMapping("posts")
    public List<PostDTO> getPosts(){
        return postService.getList();
    }

    //    게시글 단일 조회
    @Operation(summary = "게시글 정보 조회", description = "게시글 정보를 조회할 수 있는 API")
    @Parameter( name = "id", description = "게시글 번호", schema = @Schema(type="number"), in = ParameterIn.PATH, required = true )
    @GetMapping("post/{id}")
    public PostDTO getPost(@PathVariable Long id){
        Optional<PostDTO> foundPost = postService.getPost(id);
        if(foundPost.isPresent()){
            return foundPost.get();
        }
        return new PostDTO();
    }

    //    게시글 작성
    @Operation(summary = "게시글 작성", description = "게시글 새로 작성할 수 있는 API")
    @ApiResponse(responseCode = "200", description = "게시글 작성 완료")
    @PostMapping("write")
    public PostDTO write(@RequestBody PostVO postVO){
        postService.write(postVO);
        Optional<PostDTO> foundPost = postService.getPost(postVO.getId());
        if(foundPost.isPresent()){
            return foundPost.get();
        }
        return new PostDTO();
    }


    //    게시글 수정
    @Operation(summary = "게시글 수정", description = "게시글 수정할 수 있는 API")
    @Parameter( name = "id", description = "게시글 번호", schema = @Schema(type="number"), in = ParameterIn.PATH, required = true )
    @ApiResponse(responseCode = "200", description = "게시글 수정 완료")
    @PutMapping("post/{id}")
    public PostDTO modify(@PathVariable Long id, @RequestBody PostVO postVO){
        postVO.setId(id);
        postService.modify(postVO);
        Optional<PostDTO> foundPost = postService.getPost(postVO.getId());
        if(foundPost.isPresent()){
            return foundPost.get();
        }
        return new PostDTO();
    }

    //    게시글 삭제
    @Operation(summary = "게시글 삭제", description = "게시글 삭제할 수 있는 API")
    @Parameter( name = "id", description = "게시글 번호", schema = @Schema(type="number"), in = ParameterIn.PATH, required = true )
    @ApiResponse(responseCode = "200", description = "게시글 삭제 완료")
    @DeleteMapping("post/{id}")
    public void delete(@PathVariable Long id){
        postService.remove(id);
    }




}