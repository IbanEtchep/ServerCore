package fr.iban.survivalcore.commands;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.iban.survivalcore.utils.HexColor;
import fr.iban.common.data.AccountProvider;
import fr.iban.common.data.Boost;
import fr.iban.common.data.GlobalBoosts;
import fr.iban.survivalcore.SurvivalCorePlugin;
import fr.iban.survivalcore.utils.ChatUtils;
import fr.iban.spartacube.data.Account;
import fr.iban.survivalcore.utils.Time;
import fr.iban.survivalcore.utils.XPProvider;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class BoostCMD implements CommandExecutor {

	private static GlobalBoosts gb = new GlobalBoosts();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		String line1 = HexColor.GREEN.getColor() + "ID   " + HexColor.GOLD2.getColor() + "||    " + HexColor.GREEN.getColor() +"VALEUR   " + HexColor.GOLD2.getColor() + "||    " + HexColor.GREEN.getColor() + "FIN DANS";
		String line2 = "§m---------------------------";
		String adminperm = "spartacube.boost.admin";
		String modperm = "spartacube.boost.check";

		if(args.length == 0) {
			((Player) sender).performCommand("boost help");
			return false;
		}

		switch (args[0].toLowerCase()) {

		///////////////////////////////////
		// Commande pour give des boosts //
		///////////////////////////////////

		case "give":
			if (args.length == 4 || args.length == 5) {
				if(sender.hasPermission(adminperm)) {
					long boostDuration = 0;
					int boost = 0;

					// On vérifie ici si le nombre en question est un long //

					try {
						boostDuration = Long.parseLong(args[3]);
					} catch (NumberFormatException e) {
						sender.sendMessage(HexColor.PINK.getColor() + "Ceci n'est pas un entier: " + HexColor.GOLD2.getColor() + args[3]);
						return true;
					}

					// On vérifie ici si le nombre en question est un int //

					try {
						boost = Integer.parseInt(args[2]);
					} catch (NumberFormatException e) {
						sender.sendMessage(HexColor.PINK.getColor() + "Veuillez fournir un nombre. '" + HexColor.GOLD2.getColor() + args[2] + HexColor.PINK.getColor() + "' n'est pas un nombre.");
						return true;
					}

					// On vérifie si le sender veut appliqué le boost au serveur //

					if (args[1].toLowerCase().equalsIgnoreCase("global")) {  

						// On vérifie si le joueur à un déjà un boost de 100%, si oui on applique le boost après que l'autre soit fini //

						if(XPProvider.getTotalGlobalBoost() == 100) {
							gb.getBoosts().add(new Boost((gb.getLastId() + 1), boost, Long.valueOf(gb.getLastEnd() + TimeUnit.SECONDS.toMillis(boostDuration))));
							return true;
						}

						// Si son boost total n'est pas de 100% on lui ajoute le boost //

						gb.getBoosts().add(new Boost((gb.getLastId() + 1), boost, Long.valueOf(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(boostDuration))));

						// On crée un scheduler qui va envoyer le boost global à la bdd //

						Bukkit.getScheduler().runTaskAsynchronously(SurvivalCorePlugin.getInstance(), new Runnable() {
							@Override
							public void run() {
								gb.saveGlobalBoostToDB();
							}
						});

						// On envoie au sender un message de confirmation // 

						sender.sendMessage(HexColor.GOLD2.getColor() + "Tu as appliqué un boost de " + HexColor.PINK.getColor() + boost + "%" + HexColor.PINK.getColor() + " au serveur" + HexColor.GOLD2.getColor() + " pour " + HexColor.PINK.getColor() + Time.calculateTime(boostDuration));
					} else {

						// On vérifie si le sender veut appliqué le boost à tout les joueurs en ligne //

						if (args[1].toLowerCase().equalsIgnoreCase("all")) {

							// On loop tout les joueurs //

							for (Player player : Bukkit.getOnlinePlayers()) {

								// On prend les comptes des joueurs qu'on a loop //

								AccountProvider ap = new AccountProvider(player.getUniqueId());
								Account account = ap.getAccount();

								// On vérifie si le joueur à un déjà un boost de 100%, si oui on applique le boost après que l'autre soit fini //

								if(XPProvider.getTotalBoost(account, ap) == 100) {
									account.getBoosts().add(new Boost((account.getLastId() + 1), boost, Long.valueOf(account.getLastEnd() + TimeUnit.SECONDS.toMillis(boostDuration))));
									sender.sendMessage(HexColor.GOLD2.getColor() + "Tu as donné un boost de " + HexColor.PINK.getColor() + boost + "%" + HexColor.GOLD2.getColor() + " à " + HexColor.PINK.getColor() + "tout le monde" + HexColor.GOLD2.getColor() + " pour " + HexColor.PINK.getColor() + Time.calculateTime(boostDuration));
									return true;
								}

								// Si son boost total n'est pas de 100% on lui ajoute le boost //

								account.getBoosts().add(new Boost((account.getLastId() + 1), boost, Long.valueOf(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(boostDuration))));

								// On envoie le compte à redis

								ap.sendAccountToRedis(account);
							}

							// On envoie au sender un message de confirmation //

							sender.sendMessage(HexColor.GOLD2.getColor() + "Tu as donné un boost de " + HexColor.PINK.getColor() + boost + "%" + HexColor.GOLD2.getColor() + " à " + HexColor.PINK.getColor() + "tout le monde" + HexColor.GOLD2.getColor() + " pour " + HexColor.PINK.getColor() + Time.calculateTime(boostDuration));
						} else {

							// Ici c'est si le sender veut give le boost à une seule personne //
							// On vérifie si le joueur existe et si le joueur est en ligne //

							if (Bukkit.getServer().getPlayer(args[1]) == null || !Bukkit.getServer().getPlayer(args[1]).isOnline()) {
								sender.sendMessage(HexColor.PINK.getColor() + "Le joueur " + HexColor.GOLD2.getColor() + args[1] + HexColor.PINK.getColor() + " n'est pas en ligne.");

								return true;
							}

							// On prend le compte redis du joueur //

							AccountProvider ap = new AccountProvider(Bukkit.getPlayer(args[1]).getUniqueId());
							Account account = ap.getAccount();

							// On vérifie si le joueur à un déjà un boost de 100%, si oui on applique le boost après que l'autre soit fini //

							if(XPProvider.getTotalBoost(account, ap) == 100) {
								account.getBoosts().add(new Boost((account.getLastId() + 1), boost, Long.valueOf(account.getLastEnd() + TimeUnit.SECONDS.toMillis(boostDuration))));
								sender.sendMessage(HexColor.GOLD2.getColor() + "Tu as donné un boost de " + HexColor.PINK.getColor() + boost + "%" + HexColor.GOLD2.getColor() + " à " + HexColor.PINK.getColor() + Bukkit.getPlayer(args[1]).getName() + HexColor.GOLD2.getColor() + " pour " + HexColor.PINK.getColor() + Time.calculateTime(boostDuration));
								ap.sendAccountToRedis(account);
								return true;
							}

							// Si son boost total n'est pas de 100% on lui ajoute le boost //

							account.getBoosts().add(new Boost((account.getLastId() + 1), boost, Long.valueOf(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(boostDuration))));

							// On envoie le compte à redis //

							ap.sendAccountToRedis(account);

							// On envoie au sender un message de confirmation //

							sender.sendMessage(HexColor.GOLD2.getColor() + "Tu as donné un boost de " + HexColor.PINK.getColor() + boost + "%" + HexColor.GOLD2.getColor() + " à " + HexColor.PINK.getColor() + Bukkit.getPlayer(args[1]).getName() + HexColor.GOLD2.getColor() + " pour " + HexColor.PINK.getColor() + Time.calculateTime(boostDuration));
						}
					}
				}
			}
			break;

			////////////////////////////////////////
			// Commande pour supprimer des boosts //
			////////////////////////////////////////	

		case "remove":
			if (args.length == 3 || args.length == 4) {
				int id = 0;

				// On vérifie ici si le nombre en question est un int //

				try {
					id = Integer.parseInt(args[2]);
				} catch (NumberFormatException e) {
					sender.sendMessage(HexColor.PINK.getColor() + "Veuillez fournir un nombre. '" + HexColor.GOLD2.getColor() + args[2] + HexColor.PINK.getColor() + "' n'est pas un nombre.");
					return true;
				}

				// On vérifie si le sender à la permission d'utiliser la commande //

				if(sender.hasPermission(adminperm)) {

					// Dans le cas ou le sender veut enlevé un boost global //

					if (args[1].toLowerCase().equalsIgnoreCase("global")) {  

						// On trouve l'id du boost que le sender veut supprimé //

						if(!gb.getBoosts().isEmpty()) {
							Iterator<Boost> it = gb.getBoosts().iterator();
							while(it.hasNext()) {
								Integer i = it.next().getId();
								if(i == id) {

									// On crée un scheduler qui va supprimer le boost global de la bdd //

									Bukkit.getScheduler().runTaskAsynchronously(SurvivalCorePlugin.getInstance(), new Runnable() {
										@Override
										public void run() {
											gb.removeGlobalBoostFromDB(i);
										}
									});
									it.remove();
								}
							}
						} else {

							// On envoie ca si il n'y aucun boost actif //

							sender.sendMessage(HexColor.PINK.getColor() + "Il n'y a aucun boost global actif !");
							return true;
						}

						// On envoie ca si le boost a été enlevé avec succès //

						sender.sendMessage(HexColor.GREEN.getColor() + "Le boost à été enlevé avec succès");	
						return true;
					}

					// Ici c'est si le sender veut give le boost à une seule personne //
					// On vérifie si le joueur existe et si le joueur est en ligne //

					if (Bukkit.getServer().getPlayer(args[1]) == null || !Bukkit.getServer().getPlayer(args[1]).isOnline()) {
						sender.sendMessage(HexColor.PINK.getColor() + "Le joueur " + HexColor.GOLD2.getColor() + args[1] + HexColor.PINK.getColor() + " n'est pas en ligne.");

						return true;
					}

					// On prend le compte redis du joueur //

					AccountProvider ap = new AccountProvider(Bukkit.getPlayer(args[1]).getUniqueId());
					Account account = ap.getAccount(); 

					// On trouve l'id du boost que le sender veut supprimé //

					if(!account.getBoosts().isEmpty()) {
						Iterator<Boost> it = account.getBoosts().iterator();
						while(it.hasNext()) {
							Integer i = it.next().getId();
							if(i == id) {
								it.remove();

								// On crée un scheduler qui va supprimer le boost du joueur de la bdd //

								Bukkit.getScheduler().runTaskAsynchronously(SurvivalCorePlugin.getInstance(), new Runnable() {
									@Override
									public void run() {
										ap.removeBoostFromDB(Bukkit.getPlayer(args[1]).getUniqueId(), i);
									}
								});

								// On envoie le compte à redis //	

								ap.sendAccountToRedis(account);
							}
						}
					} else {

						// On envoie ca si le joueur n'a aucun boost actif //

						sender.sendMessage(HexColor.PINK.getColor() + "Ce joueur n'a aucun boost actif !");
						break;
					}

					// On envoie ca si le boost a été enlevé avec succès //

					sender.sendMessage(HexColor.GREEN.getColor() + "Le boost à été enlevé avec succès");	
				}
			} else {

				// On envoie ca si la commande est invalide //

				sender.sendMessage(HexColor.PINK.getColor() + "Commande invalide. /boost remove (joueur) (id)");
			}
			break;

			///////////////////////////////////
			// Commande pour voir ses boosts //
			///////////////////////////////////    

		case "show":
			if(args.length == 1) {

				// On prend le compte redis du sender //

				AccountProvider ap = new AccountProvider(Bukkit.getPlayer(((Player) sender).getName()).getUniqueId());
				Account account = ap.getAccount();

				// On vérifie si il a des boosts actifs //

				if(XPProvider.getTotalBoost(account, ap) != 0) {
					sender.sendMessage(line1);
					sender.sendMessage(line2);
					for (Boost boost : account.getBoosts()) {
						sender.sendMessage("" + HexColor.EXTRA_BLUE.getColor() + boost.getId() + HexColor.GOLD2.getColor() + "    ||     " + HexColor.EXTRA_BLUE.getColor() + boost.getValue() + "%     " + HexColor.GOLD2.getColor() + "||    "  + HexColor.EXTRA_BLUE.getColor() + Time.calculateTime(TimeUnit.MILLISECONDS.toSeconds(boost.getEnd() - System.currentTimeMillis())));
					}
					sender.sendMessage(line2);
					ap.sendAccountToRedis(account);
				} else {
					sender.sendMessage(HexColor.PINK.getColor() + "Vous n'avez aucun boost actif !");
				}
			} else {

				// Dans le cas ou il veut voir les boosts globals // 

				if (args.length == 2 || args.length == 3) {
					if(args[1].toLowerCase().equalsIgnoreCase("global")) {

						// On prend le compte redis du joueur //

						if(XPProvider.getTotalGlobalBoost() != 0) {
							sender.sendMessage(line1);
							sender.sendMessage(line2);
							for (Boost boost : gb.getBoosts()) {
								sender.sendMessage("" + HexColor.EXTRA_BLUE.getColor() + boost.getId() + HexColor.GOLD2.getColor() + "    ||     " + HexColor.EXTRA_BLUE.getColor() + boost.getValue() + "%     " + HexColor.GOLD2.getColor() + "||    " + HexColor.EXTRA_BLUE.getColor() + Time.calculateTime(TimeUnit.MILLISECONDS.toSeconds(boost.getEnd() - System.currentTimeMillis())));
							}
							sender.sendMessage(line2);
						} else {
							sender.sendMessage(HexColor.PINK.getColor() + "Il n'y a pas de boost global actif !");
						}	
					} else {
						if (sender.hasPermission(modperm)) {
							if (Bukkit.getServer().getPlayer(args[1]) == null || !Bukkit.getServer().getPlayer(args[1]).isOnline()) {
								sender.sendMessage(HexColor.PINK.getColor() + "Le joueur " + HexColor.GOLD2.getColor() + args[1] + " n'est pas en ligne.");

								return true;
							} else {
								AccountProvider ap = new AccountProvider(Bukkit.getPlayer(args[1]).getUniqueId());
								Account account = ap.getAccount();

								if(XPProvider.getTotalBoost(account, ap) != 0) {
									sender.sendMessage(line1);
									sender.sendMessage(line2);
									for (Boost boost : account.getBoosts()) {
										sender.sendMessage("" + HexColor.EXTRA_BLUE.getColor() + boost.getId() + HexColor.GOLD2.getColor() + "    ||     " + HexColor.EXTRA_BLUE.getColor() + boost.getValue() + "%     " + HexColor.GOLD2.getColor() + "||    " + HexColor.EXTRA_BLUE.getColor() + Time.calculateTime(TimeUnit.MILLISECONDS.toSeconds(boost.getEnd() - System.currentTimeMillis())));
									}
									sender.sendMessage(line2);
								} else {
									sender.sendMessage(HexColor.PINK.getColor() + "Ce joueur n'a aucun boost actif !");
								}
							}
						}
					}
				}
			}
			break;

			//////////////////////////////////////
			// Commande pour de help des boosts //
			//////////////////////////////////////

		case "help":
			sender.sendMessage(HexColor.MARRON_CLAIR.getColor() + "La gestion des boosts se gère avec les commandes ci-dessous.");
			sender.sendMessage("");
			if(!sender.hasPermission(modperm)) {
				sender.sendMessage(getCommandUsage("/boost show (global)", "Permet d'afficher vos boosts ou les boosts globaux."));
			} else {
				sender.sendMessage(getCommandUsage("/boost show (joueur/global)", "Permet d'afficher vos boost, les boosts d'un autre joueur ou les boost globaux"));		
			}
			if(sender.hasPermission(adminperm)) {
				sender.sendMessage(getCommandUsage("/boost give (joueur/all/global) (pourcentage) (temps) ", "Permet de give un boost à un joueur"));	
				sender.sendMessage(getCommandUsage("/boost remove (joueur/global) (id)", "Permet d'enlever un boost à un joueur"));	
			}
			sender.sendMessage("");
			break;
		default:
			break;
		}
		return false;
	}

	private BaseComponent[] getCommandUsage(String command, String desc) {
		ComponentBuilder builder = new ComponentBuilder("- ").color(HexColor.MARRON_CLAIR.getColor());
		builder.append(new ComponentBuilder(command)
				.event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, command))
				.event(ChatUtils.getShowTextHoverEvent(ChatColor.GRAY + "Clic pour écrire la commande"))
				.color(HexColor.MARRON.getColor()).create());
		builder.append(new ComponentBuilder(" - ").color(HexColor.MARRON_CLAIR.getColor()).append(desc).color(HexColor.MARRON_CLAIR.getColor()).create());
		return builder.create();
	}

}
