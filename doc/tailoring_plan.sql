/*
 Navicat Premium Data Transfer

 Source Server         : 39.98.239.104
 Source Server Type    : SQL Server
 Source Server Version : 14003048
 Source Host           : 39.98.239.104:1433
 Source Catalog        : XIANAV2
 Source Schema         : dbo

 Target Server Type    : SQL Server
 Target Server Version : 14003048
 File Encoding         : 65001

 Date: 19/12/2019 21:23:59
*/


-- ----------------------------
-- Table structure for tailoring_plan
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[tailoring_plan]') AND type IN ('U'))
	DROP TABLE [dbo].[tailoring_plan]
GO

CREATE TABLE [dbo].[tailoring_plan] (
  [id] bigint  IDENTITY(1,1) NOT NULL,
  [work_order_no] nvarchar(32) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [status] nvarchar(2) COLLATE Chinese_PRC_CI_AS  NULL,
  [type_number] nvarchar(32) COLLATE Chinese_PRC_CI_AS  NULL,
  [group] nvarchar(16) COLLATE Chinese_PRC_CI_AS  NULL,
  [member] nvarchar(32) COLLATE Chinese_PRC_CI_AS  NULL,
  [main_supplement] nvarchar(2) COLLATE Chinese_PRC_CI_AS  NULL,
  [quantity] int DEFAULT ((0)) NOT NULL,
  [due_date] datetime2(0)  NULL,
  [product_line_no] int  NULL,
  [product_code] nvarchar(32) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [fabric_code] nvarchar(32) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [fabric_width] float(53)  NULL,
  [fabric_colour] nvarchar(4) COLLATE Chinese_PRC_CI_AS  NULL,
  [box_quantity] int  NULL,
  [binding_quantity] int  NULL,
  [change_pieces_quantity] int  NULL,
  [create_time] datetime2(0) DEFAULT (getdate()) NOT NULL,
  [update_time] datetime2(0) DEFAULT (getdate()) NOT NULL,
  [max_change_pieces_quantity] int  NULL,
  [max_quantity] int  NULL,
  [type_quantity] int  NULL,
  [max_floor_height] int  NULL,
  [work_quantity] int DEFAULT ((0)) NULL,
  [work_change_pieces_quantity] int DEFAULT ((0)) NULL
)
GO

ALTER TABLE [dbo].[tailoring_plan] SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'裁剪计划ID',
'SCHEMA', N'dbo',
'TABLE', N'tailoring_plan',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'工单号',
'SCHEMA', N'dbo',
'TABLE', N'tailoring_plan',
'COLUMN', N'work_order_no'
GO

EXEC sp_addextendedproperty
'MS_Description', N'计划状态, 1 打开 , 2 关闭',
'SCHEMA', N'dbo',
'TABLE', N'tailoring_plan',
'COLUMN', N'status'
GO

EXEC sp_addextendedproperty
'MS_Description', N'版号',
'SCHEMA', N'dbo',
'TABLE', N'tailoring_plan',
'COLUMN', N'type_number'
GO

EXEC sp_addextendedproperty
'MS_Description', N'组别',
'SCHEMA', N'dbo',
'TABLE', N'tailoring_plan',
'COLUMN', N'group'
GO

EXEC sp_addextendedproperty
'MS_Description', N'组员',
'SCHEMA', N'dbo',
'TABLE', N'tailoring_plan',
'COLUMN', N'member'
GO

EXEC sp_addextendedproperty
'MS_Description', N'主/辅/补',
'SCHEMA', N'dbo',
'TABLE', N'tailoring_plan',
'COLUMN', N'main_supplement'
GO

EXEC sp_addextendedproperty
'MS_Description', N'计划数量',
'SCHEMA', N'dbo',
'TABLE', N'tailoring_plan',
'COLUMN', N'quantity'
GO

EXEC sp_addextendedproperty
'MS_Description', N'出货日期',
'SCHEMA', N'dbo',
'TABLE', N'tailoring_plan',
'COLUMN', N'due_date'
GO

EXEC sp_addextendedproperty
'MS_Description', N'产品行号',
'SCHEMA', N'dbo',
'TABLE', N'tailoring_plan',
'COLUMN', N'product_line_no'
GO

EXEC sp_addextendedproperty
'MS_Description', N'产品编码',
'SCHEMA', N'dbo',
'TABLE', N'tailoring_plan',
'COLUMN', N'product_code'
GO

EXEC sp_addextendedproperty
'MS_Description', N'布料编码',
'SCHEMA', N'dbo',
'TABLE', N'tailoring_plan',
'COLUMN', N'fabric_code'
GO

EXEC sp_addextendedproperty
'MS_Description', N'布料幅宽',
'SCHEMA', N'dbo',
'TABLE', N'tailoring_plan',
'COLUMN', N'fabric_width'
GO

EXEC sp_addextendedproperty
'MS_Description', N'布料颜色',
'SCHEMA', N'dbo',
'TABLE', N'tailoring_plan',
'COLUMN', N'fabric_colour'
GO

EXEC sp_addextendedproperty
'MS_Description', N'装箱数量',
'SCHEMA', N'dbo',
'TABLE', N'tailoring_plan',
'COLUMN', N'box_quantity'
GO

EXEC sp_addextendedproperty
'MS_Description', N'扎单数量',
'SCHEMA', N'dbo',
'TABLE', N'tailoring_plan',
'COLUMN', N'binding_quantity'
GO

EXEC sp_addextendedproperty
'MS_Description', N'换片数量',
'SCHEMA', N'dbo',
'TABLE', N'tailoring_plan',
'COLUMN', N'change_pieces_quantity'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'tailoring_plan',
'COLUMN', N'create_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'更新时间',
'SCHEMA', N'dbo',
'TABLE', N'tailoring_plan',
'COLUMN', N'update_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'最大裁剪数',
'SCHEMA', N'dbo',
'TABLE', N'tailoring_plan',
'COLUMN', N'work_quantity'
GO

EXEC sp_addextendedproperty
'MS_Description', N'最大换片数',
'SCHEMA', N'dbo',
'TABLE', N'tailoring_plan',
'COLUMN', N'work_change_pieces_quantity'
GO

EXEC sp_addextendedproperty
'MS_Description', N'裁剪计划表',
'SCHEMA', N'dbo',
'TABLE', N'tailoring_plan'
GO


-- ----------------------------
-- Records of tailoring_plan
-- ----------------------------
SET IDENTITY_INSERT [dbo].[tailoring_plan] ON
GO

INSERT INTO [dbo].[tailoring_plan] ([id], [work_order_no], [status], [type_number], [group], [member], [main_supplement], [quantity], [due_date], [product_line_no], [product_code], [fabric_code], [fabric_width], [fabric_colour], [box_quantity], [binding_quantity], [change_pieces_quantity], [create_time], [update_time], [max_change_pieces_quantity], [max_quantity], [type_quantity], [max_floor_height], [work_quantity], [work_change_pieces_quantity]) VALUES (N'141', N'4B-9750', N'1', N'电脑版', NULL, NULL, NULL, N'1040', N'2020-01-20 00:00:00', N'20000', N'YE30-T-00-705-03', N'FNA30YEA02', N'1.55', N'YE', N'4', N'2', N'10', N'2019-12-19 11:30:58', N'2019-12-19 11:30:58', N'10', N'1040', NULL, NULL, N'0', N'0')
GO

INSERT INTO [dbo].[tailoring_plan] ([id], [work_order_no], [status], [type_number], [group], [member], [main_supplement], [quantity], [due_date], [product_line_no], [product_code], [fabric_code], [fabric_width], [fabric_colour], [box_quantity], [binding_quantity], [change_pieces_quantity], [create_time], [update_time], [max_change_pieces_quantity], [max_quantity], [type_quantity], [max_floor_height], [work_quantity], [work_change_pieces_quantity]) VALUES (N'142', N'2A-9512', N'-1', N'18号（共7衣片）', NULL, NULL, NULL, N'1440', N'2020-01-14 00:00:00', N'10000', N'WH20-T-04-134-07', N'FNA20WHA02', N'1.53', N'WH', N'40', N'1', N'14', N'2019-12-19 11:31:36', N'2019-12-19 11:32:35', N'14', N'1440', NULL, NULL, N'0', N'0')
GO

INSERT INTO [dbo].[tailoring_plan] ([id], [work_order_no], [status], [type_number], [group], [member], [main_supplement], [quantity], [due_date], [product_line_no], [product_code], [fabric_code], [fabric_width], [fabric_colour], [box_quantity], [binding_quantity], [change_pieces_quantity], [create_time], [update_time], [max_change_pieces_quantity], [max_quantity], [type_quantity], [max_floor_height], [work_quantity], [work_change_pieces_quantity]) VALUES (N'150', N'4B-9714', N'1', N'电脑版', NULL, NULL, NULL, N'36', N'2019-12-17 00:00:00', N'70000', N'GR40-T-04-126-05', N'FNA40GRA02', N'1.55', N'GR', N'4', N'2', N'0', N'2019-12-19 21:18:05', N'2019-12-19 21:18:05', N'0', N'36', NULL, NULL, NULL, NULL)
GO

SET IDENTITY_INSERT [dbo].[tailoring_plan] OFF
GO


-- ----------------------------
-- Auto increment value for tailoring_plan
-- ----------------------------
DBCC CHECKIDENT ('[dbo].[tailoring_plan]', RESEED, 150)
GO


-- ----------------------------
-- Primary Key structure for table tailoring_plan
-- ----------------------------
ALTER TABLE [dbo].[tailoring_plan] ADD CONSTRAINT [PK__tailorin__3213E83FA09FA15E] PRIMARY KEY CLUSTERED ([id])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO

