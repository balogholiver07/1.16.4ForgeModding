package com.velior7.tutorialmod.common.blocks;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IWorld;

public class BaseHorizontalBlock extends Block {
	
	protected static final Map<Direction, VoxelShape> SHAPES_MAP = new HashMap<Direction, VoxelShape>();
	
	public static final DirectionProperty HORIZONAL_FACING = BlockStateProperties.HORIZONTAL_FACING;

	public BaseHorizontalBlock(Properties properties) {
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONAL_FACING, Direction.NORTH));
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.toRotation(state.get(HORIZONAL_FACING)));
	}
	
	@Override
	public BlockState rotate(BlockState state, IWorld world, BlockPos pos, Rotation direction) {
		return state.with(HORIZONAL_FACING, direction.rotate(state.get(HORIZONAL_FACING)));
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState().with(HORIZONAL_FACING, context.getPlacementHorizontalFacing().getOpposite());
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(HORIZONAL_FACING);
	}
	
	protected static final Map<Direction, VoxelShape> SHAPES = new HashMap<Direction, VoxelShape>();
	 
	protected static void calculateShapes(Direction to, VoxelShape shape) {
	    VoxelShape[] buffer = new VoxelShape[] { shape, VoxelShapes.empty() };
	 
	    int times = (to.getHorizontalIndex() - Direction.NORTH.getHorizontalIndex() + 4) % 4;
	    for (int i = 0; i < times; i++) {
	        buffer[0].forEachBox((minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = VoxelShapes.or(buffer[1],
	                VoxelShapes.create(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX)));
	        buffer[0] = buffer[1];
	        buffer[1] = VoxelShapes.empty();
	    }
	 
	    SHAPES.put(to, buffer[0]);
	}
	 
	protected void runCalculation(VoxelShape shape) {
	    for (Direction direction : Direction.values()) {
	        calculateShapes(direction, shape);
	    }
	}
	
}