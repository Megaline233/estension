package xyz.Extencion.extencion.managers;

import  xyz.Extencion.extencion.event.EventBus;
import  xyz.Extencion.extencion.modules.api.Category;
import  xyz.Extencion.extencion.modules.api.Feature;
import  xyz.Extencion.extencion.modules.api.Module;
import  xyz.Extencion.extencion.modules.impl.client.*;
import  xyz.Extencion.extencion.modules.impl.exploit.*;
import  xyz.Extencion.extencion.modules.impl.movement.*;
import  xyz.Extencion.extencion.modules.impl.misc.*;
import  xyz.Extencion.extencion.modules.impl.player.*;

import java.util.*;
import java.util.stream.Collectors;

import static  xyz.Extencion.extencion.QuickImports.mc;

public class ModuleManager {
    public List<Module> modules = new ArrayList<>();
    public List<Module> sortedModules = new ArrayList<>();
    public List<String> sortedModulesABC = new ArrayList<>();
    public List<Integer> activeMouseKeys = new ArrayList<>();

    public void init() {
        modules.add(new Speed());
        modules.add(new AutoRespawn());
        modules.add(new FastUse());
        modules.add(new AutoSprint());
        modules.add(new GuiMove());
        modules.add(new NoPush());
        modules.add(new ClientName());
        modules.add(new HitboxDesync());
        modules.add(new Notifications());
        modules.add(new ItemScroller());
    }

    public Module getModuleByName(String name) {
        for (Module module : this.modules) {
            if (!module.getName().equalsIgnoreCase(name)) continue;
            return module;
        }
        return null;
    }

    public <T extends Module> T getModuleByClass(Class<T> clazz) {
        for (Module module : this.modules) {
            if (!clazz.isInstance(module)) continue;
            return (T) module;
        }
        return null;
    }

    public void enableModule(Class<Module> clazz) {
        Module module = this.getModuleByClass(clazz);
        if (module != null) {
            module.enable();
        }
    }

    public void disableModule(Class<Module> clazz) {
        Module module = this.getModuleByClass(clazz);
        if (module != null) {
            module.disable();
        }
    }

    public void enableModule(String name) {
        Module module = this.getModuleByName(name);
        if (module != null) {
            module.enable();
        }
    }

    public void disableModule(String name) {
        Module module = this.getModuleByName(name);
        if (module != null) {
            module.disable();
        }
    }

    public boolean isModuleEnabled(String name) {
        Module module = this.getModuleByName(name);
        return module != null && module.isOn();
    }

    public boolean isModuleEnabled(Class<Module> clazz) {
        Module module = this.getModuleByClass(clazz);
        return module != null && module.isOn();
    }

    public Module getModuleByDisplayName(String displayName) {
        for (Module module : this.modules) {
            if (!module.getDisplayName().equalsIgnoreCase(displayName)) continue;
            return module;
        }
        return null;
    }

    public ArrayList<Module> getEnabledModules() {
        ArrayList<Module> enabledModules = new ArrayList<>();
        for (Module module : this.modules) {
            if (!module.isEnabled()) continue;
            enabledModules.add(module);
        }
        return enabledModules;
    }

    public ArrayList<String> getEnabledModulesName() {
        ArrayList<String> enabledModules = new ArrayList<>();
        for (Module module : this.modules) {
            if (!module.isEnabled() || !module.isDrawn()) continue;
            enabledModules.add(module.getFullArrayString());
        }
        return enabledModules;
    }

    public ArrayList<Module> getModulesByCategory(Category category) {
        ArrayList<Module> modulesCategory = new ArrayList<Module>();
        this.modules.forEach(module -> {
            if (module.getCategory() == category) {
                modulesCategory.add(module);
            }
        });
        return modulesCategory;
    }

    public List<Category> getCategories() {
        return Arrays.asList(Category.values());
    }

    public void onLoad() {
        this.modules.stream().filter(Module::listening).forEach(EventBus::register);
        this.modules.forEach(Module::onLoad);
    }

    public void onUpdate() {
        this.modules.stream().filter(Feature::isEnabled).forEach(Module::onUpdate);
    }

    public void onTick() {
        this.modules.stream().filter(Feature::isEnabled).forEach(Module::onTick);
    }

//    public void onRender2D(Render2DEvent event) {
//        this.modules.stream().filter(Feature::isEnabled).forEach(module -> module.onRender2D(event));
//    }
//
//    public void onRender3D(Render3DEvent event) {
//        this.modules.stream().filter(Feature::isEnabled).forEach(module -> module.onRender3D(event));
//    }

    public void sortModules(boolean reverse) {
        this.sortedModules = this.getEnabledModules().stream().filter(Module::isDrawn)
                .sorted(Comparator.comparing(module -> mc.textRenderer.getWidth(module.getFullArrayString()) * (reverse ? -1 : 1)))
                .collect(Collectors.toList());
    }

    public void sortModulesABC() {
        this.sortedModulesABC = new ArrayList<>(this.getEnabledModulesName());
        this.sortedModulesABC.sort(String.CASE_INSENSITIVE_ORDER);
    }

    public void onUnload() {
        this.modules.forEach(EventBus::unregister);
        this.modules.forEach(Module::onUnload);
    }

    public void onUnloadPost() {
        for (Module module : this.modules) {
            module.enabled.setValue(false);
        }
    }

    public void onKeyPressed(int eventKey) {
        if (eventKey <= 0) return;
        this.modules.forEach(module -> {
            if (module.getBind().getKey() == eventKey) {
                module.toggle();
            }
        });
    }

    public void onMoseKeyReleased(int eventKey) {
        activeMouseKeys.add(eventKey);
    }
}