package org.example.ohgiraffers.fiveguyz.controller;

import lombok.RequiredArgsConstructor;
import org.example.ohgiraffers.fiveguyz.entity.Board;
import org.example.ohgiraffers.fiveguyz.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/board/write")
    public String boardWriteForm() {
        return "fiveguyzwrite";
    }

    @PostMapping("/board/writedo")
    public String boardWritePro(Board board, Model model, MultipartFile file) throws Exception{
        boardService.write(board, file);

        return "message";
    }

    @GetMapping("/board/list")
    public String boardList(Model model, @PageableDefault(page = 0, size = 10, sort = "Post_ID", direction = Sort.Direction.DESC) Pageable pageable){


        Page<Board> list = boardService.boardlist(pageable);

        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "boardlist";
    }

    @GetMapping("/board/view")
    public String boardview(Model model, Integer Post_ID){
        model.addAttribute("board", boardService.boardview(Post_ID));
        return "boardview";
    }

    @GetMapping("/board/delete")
    public String boardDelete(Integer Post_ID){
        boardService.boardDelete(Post_ID);
        return "redirect:/board/list";
    }

    @GetMapping("/board/modify/{Post_ID}")
    public String boardModify(@PathVariable("Post_ID") Integer Post_ID, Model model){
        model.addAttribute("board", boardService.boardview(Post_ID));
        return "boardModify";
    }

    @GetMapping("/board/update/{Post_ID}")
    public String boardUpdate(@PathVariable("Post_ID") Integer Post_ID, Board board, Model model, MultipartFile file) throws Exception{

        Board boardTemp = boardService.boardview(Post_ID);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        boardService.write(boardTemp, file);

        return "redirect:/board/list";
    }
}