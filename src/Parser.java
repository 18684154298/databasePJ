import ConcreteCommand.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Parser {
    public static Date parseSqlDate(String dateStr) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
        try {
            java.util.Date utilDate = formatter.parse(dateStr);
            return new Date(utilDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    static String user_type = null;
    static String username = null;
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter command:");

        while(scanner.hasNextLine()) {
            String input = scanner.nextLine();
            String[] commands = input.split(" ");

            switch (commands[0]) {
                case "register_costumer":
                case "rc":
                    if (commands.length == 5) {
                        Register.registerCostumer(commands[1], commands[2],commands[3],commands[4]);
                    } else {
                        System.out.println("Invalid register command. Usage: rc <name> <age> <gender> <tel>");
                    }
                    break;
                case "showAllCommodity":
                case "sac":
                    ShowAllCommodity.show_all_commodity();
                    break;
                case "favorite":
                    if (commands.length == 3) {
                        favorite.favorite(username,Integer.parseInt(commands[1]),Integer.parseInt(commands[2]));
                    } else {
                        System.out.println("Invalid register command. Usage: favorite <username> <commodity_id> <threshold>");
                    }
                    break;
                case "register_vendor":
                case "rv":
                    if (commands.length == 3) {
                        RegisterVendor.registerVendor(commands[1], commands[2]);
                    } else {
                        System.out.println("Invalid register command. Usage: rv <name> <address>");
                    }
                    break;
                // 可以在这里增加更多命令的处理
                case "login" :
                    if (commands.length == 3) {
                        if (commands[1].equals("vendor")) {
                            if (SelectVendor.selectVendorByName(commands[2])) {
                                username = commands[2];
                                user_type = "vendor";
                                System.out.println("商家登录成功");
                            } else {
                                System.out.println("未注册");
                            }
                        }
                        if (commands[1].equals("customer")) {
                            if (SelectCustomer.selectCustomerByName(commands[2])) {
                                username = commands[2];
                                user_type = "customer";
                                System.out.println("用户登录成功");
                            } else {
                                System.out.println("未注册");
                            }

                        }
                    }else if(commands.length == 2){
                        if (commands[1].equals("administrator")){
                            username = "administrator";
                            user_type = "administrator";
                            System.out.println("管理员登录成功");
                        }
                    } else {
                        System.out.println("Invalid register command. Usage: login <usertype> <username>");
                    }
                    break;
                case "sell":
                    if (user_type != null && user_type.equals("vendor") && commands.length == 5) {
                        // 使用 username 作为 vendorName，它应该已经通过之前的 "login" 命令设置好了
                        String commodityName = commands[1];
                        String category = commands[2];
                        String origin = commands[3];
                        Date mfd = parseSqlDate(commands[4]); // 解析日期

                        if (mfd != null) {
                            CreateCommodity.addCommodity(username, commodityName, category, origin, mfd);
                        } else {
                            System.out.println("Invalid date format. Usage: sell <name> <category> <origin> <yyyy.MM.dd>");
                        }
                    } else {
                        if (user_type == null || !user_type.equals("vendor")) {
                            System.out.println("Error: Only vendors can sell items.");
                        } else {
                            System.out.println("Invalid sell command. Usage: sell <name> <category> <origin> <yyyy.MM.dd>");
                        }
                    }
                    break;
                case "publish":
                    if (user_type != null && user_type.equals("vendor") && commands.length == 5 && "to".equals(commands[2])) {
                        String commodityName = commands[1];
                        String platformName = commands[3];
                        BigDecimal bigDecimalNumber = new BigDecimal(commands[4]);
                        PublishCommodity.publishCommodity(username, commodityName, platformName,bigDecimalNumber);
                    } else {
                        System.out.println("Invalid publish command. Usage: publish <commodity_name> to <platform_name>");
                    }
                    break;
                case "modify":
                    if (user_type != null && user_type.equals("vendor") && commands.length == 5 && "in".equals(commands[2])) {
                        String commodityName = commands[1];
                        String platformName = commands[3];
                        BigDecimal bigDecimalNumber = new BigDecimal(commands[4]);
                        ModifyCommodityPrice.modifyCommodityPrice(username, commodityName, platformName,bigDecimalNumber);
                    } else {
                        System.out.println("Invalid publish command. Usage: publish <commodity_name> to <platform_name>");
                    }
                    break;
                case "showMyInfo":
                    if(user_type.equals("vendor")) VendorQuery.queryVendorInfo(username);
                    else if(user_type.equals("customer")) VendorQuery.queryCustomerInfo(username);
                    break;
                case "showMyCommodity":
                    VendorQuery.queryVendorCommodities(username);
                    break;
                case "searchMC":
                    VendorQuery.queryLatestPrice(username,commands[1]);
                    break;
                case "notice":
                    CustomerNotifications.notice(username);
                    break;
                case "administer":
                    if(commands[1].equals("mv")){
                        Administer.updateVendor(Integer.parseInt(commands[2]),commands[3],commands[4]);
                    }else if(commands[1].equals("dv")){
                        Administer.deleteVendor(Integer.parseInt(commands[2]));
                    }else if(commands[1].equals("mc")){
                        Administer.updateCustomer(Integer.parseInt(commands[2]),commands[3],Integer.parseInt(commands[4]),commands[5],commands[6]);
                    }else if(commands[1].equals("dc")){
                        Administer.deleteCustomer(Integer.parseInt(commands[2]));
                    }else if(commands[1].equals("deleteCommodity")){
                        Administer.deleteCommodity(Integer.parseInt(commands[2]));
                    }
                    break;
                case "search_commodity":
                    if(commands.length == 2){
                        ShowAllCommodity.showCommodityById(Integer.parseInt(commands[2]));
                    }else{
                        System.out.println("wrong command");
                    }
                    break;
                case "show_my_favorite":
                    if(user_type.equals("customer")){
                        ShowAllCommodity.show_my_favorite_commodity(username);
                    }else{
                        System.out.println("you are not a customer");
                    }
                    break;
                default:
                    System.out.println("Unknown command.");
                    break;
            }
        }
    }
}
