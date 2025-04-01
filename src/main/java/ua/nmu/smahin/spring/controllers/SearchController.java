package ua.nmu.smahin.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.nmu.smahin.spring.models.ItemModel;
import ua.nmu.smahin.spring.services.ParseService;

import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private ParseService parseService;

    @GetMapping
    public String searchGet() {
        return "search";
    }

    @PostMapping
    public String searchPost(@RequestParam String query, Model model) {
        List<ItemModel> itemList = parseService.parseByQuery(query);
        model.addAttribute("items", itemList);
        return "result";
    }
}
