package net.xxs.service.impl;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;

import net.xxs.bean.CardsImage;
import net.xxs.bean.Setting;
import net.xxs.service.CardsImageService;
import net.xxs.util.CommonUtil;
import net.xxs.util.ImageUtil;
import net.xxs.util.SettingUtil;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;

/**
 * Service实现类 - 充值卡图片
 */

@Service("cardsImageServiceImpl")
public class CardsImageServiceImpl implements CardsImageService, ServletContextAware {
	
	private ServletContext servletContext;
	
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	public CardsImage buildCardsImage(File cardsImageFile) {
		Setting setting = SettingUtil.getSetting();
		String uuid = CommonUtil.getUUID();
		
		CardsImage cardsImage = new CardsImage();
		cardsImage.setId(uuid);
		cardsImage.setPath(setting.getImageUploadRealPath());
		cardsImage.setSourceImageFormatName(ImageUtil.getFormatName(cardsImageFile));
		
		File sourceCardsImageFile = new File(servletContext.getRealPath(cardsImage.getSourceImagePath()));
		File bigCardsImageFile = new File(servletContext.getRealPath(cardsImage.getBigImagePath()));
		File smallCardsImageFile = new File(servletContext.getRealPath(cardsImage.getSmallImagePath()));
		File thumbnailCardsImageFile = new File(servletContext.getRealPath(cardsImage.getThumbnailImagePath()));
		File watermarkImageFile = new File(servletContext.getRealPath(setting.getWatermarkImagePath()));

		File sourceCardsImageParentFile = sourceCardsImageFile.getParentFile();
		File bigCardsImageParentFile = bigCardsImageFile.getParentFile();
		File smallCardsImageParentFile = smallCardsImageFile.getParentFile();
		File thumbnailCardsImageParentFile = thumbnailCardsImageFile.getParentFile();

		if (!sourceCardsImageParentFile.isDirectory()) {
			sourceCardsImageParentFile.mkdirs();
		}
		if (!bigCardsImageParentFile.isDirectory()) {
			bigCardsImageParentFile.mkdirs();
		}
		if (!smallCardsImageParentFile.isDirectory()) {
			smallCardsImageParentFile.mkdirs();
		}
		if (!thumbnailCardsImageParentFile.isDirectory()) {
			thumbnailCardsImageParentFile.mkdirs();
		}
		
		try {
			FileUtils.copyFile(cardsImageFile, sourceCardsImageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageUtil.reduceAndImageWatermark(cardsImageFile, bigCardsImageFile, setting.getBigCardsImageWidth(), setting.getBigCardsImageHeight(), watermarkImageFile, setting.getWatermarkPosition(), setting.getWatermarkAlpha());
		ImageUtil.reduceAndImageWatermark(cardsImageFile, smallCardsImageFile, setting.getSmallCardsImageWidth(), setting.getSmallCardsImageHeight(), watermarkImageFile, setting.getWatermarkPosition(), setting.getWatermarkAlpha());
		ImageUtil.reduce(cardsImageFile, thumbnailCardsImageFile, setting.getThumbnailCardsImageHeight(), setting.getThumbnailCardsImageWidth());
		
		return cardsImage;
	}

}