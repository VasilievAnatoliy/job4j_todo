package ru.job4j.todo.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.ItemService;

import javax.servlet.http.HttpSession;

@Controller
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/items")
    public String items(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("user", user);
        model.addAttribute("items", itemService.findAll());
        return "items";
    }

    @GetMapping("/formAddItem")
    public String addItem(@ModelAttribute Item item, Model model, HttpSession session) {
         User user = (User) session.getAttribute("user");
         model.addAttribute("user", user);
        return "addItem";
    }

    @PostMapping("/createItem")
    public String createItem(@ModelAttribute Item item) {
        itemService.add(item);
        return "redirect:/items";
    }

    @GetMapping("/formNewItems")
    public String allNewItems(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("items", itemService.findAllNotDone());
        return "items";
    }

    @GetMapping("/formDoneItems")
    public String allDoneItems(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("items", itemService.findAllDone());
        return "items";
    }

    @GetMapping("/formDescriptionItem/{itemId}")
    public String descriptionItem(
            Model model, HttpSession session, @PathVariable("itemId") int id) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        Item item = itemService.findById(id);
        model.addAttribute("item", item);
        return "descriptionItem";
    }

    @GetMapping("/formUpdate/{itemId}")
    public String formUpdateItem(
            Model model, HttpSession session, @PathVariable("itemId") int id) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        Item item = itemService.findById(id);
        model.addAttribute("item", item);
        return "updateItem";
    }

    @PostMapping("/updateItem")
    public String updateItem(@ModelAttribute Item item) {
        itemService.update(item);
        return "redirect:/items";
    }

    @GetMapping("/formDelete/{itemId}")
    public String deleteItem(@PathVariable("itemId") int id) {
        itemService.delete(id);
        return "redirect:/items";
    }

    @GetMapping("/formDone/{itemId}")
    public String doneItem(@PathVariable("itemId") int id) {
        itemService.done(id);
        return "redirect:/items";
    }
}
