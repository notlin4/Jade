package snownee.jade.util;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.google.common.collect.Sets;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import snownee.jade.Jade;
import snownee.jade.api.IJadeProvider;
import snownee.jade.api.ITooltip;

public class WailaExceptionHandler {

	private static final Set<IJadeProvider> ERRORS = Sets.newHashSet();
	private static final File ERROR_OUTPUT = new File("logs", "JadeErrorOutput.txt");
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy - HH:mm:ss");

	public static void handleErr(Throwable e, IJadeProvider provider, ITooltip tooltip) {
		if (!ERRORS.contains(provider)) {
			ERRORS.add(provider);

			Jade.LOGGER.error("Caught unhandled exception : [{}] {}", provider, e);
			Jade.LOGGER.error("See JadeErrorOutput.txt for more information");
			try {
				FileUtils.writeStringToFile(ERROR_OUTPUT, DATE_FORMAT.format(new Date()) + "\n" + provider + "\n" + ExceptionUtils.getStackTrace(e) + "\n", StandardCharsets.UTF_8, true);
			} catch (Exception what) {
				// no
			}
		}
		if (tooltip != null)
			tooltip.add(new TranslatableComponent("jade.error", ModIdentification.getModName(provider.getUid())).withStyle(ChatFormatting.DARK_RED));
	}
}
