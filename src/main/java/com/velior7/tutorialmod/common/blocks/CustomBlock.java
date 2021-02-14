package com.velior7.tutorialmod.common.blocks;

import java.util.stream.Stream;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class CustomBlock extends BaseHorizontalBlock {

	private static final VoxelShape SHAPE = 
			Stream.of(Block.makeCuboidShape(0, 0, 0, 16, 2, 16),
					Block.makeCuboidShape(1, 2, 1, 15, 4, 15),
					Block.makeCuboidShape(2.7, 4, 2, 13.700000000000006, 6, 14),
					Block.makeCuboidShape(0, 2, 15, 15, 11, 16)
					).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

	public CustomBlock(Properties properties) {
		super(properties);
		runCalculation(SHAPE);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPES.get(state.get(HORIZONAL_FACING));
	}

}
