import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Wall implements Structure {
	
	private List<Block> blocks;

	@Override
	public Optional<Block> findBlockByColor(String color) {
		if (color == null || blocks == null  || blocks.isEmpty()) {
			return Optional.empty();
		}
		return findBlockByColorRecursive(color, blocks);
	}
	
	private Optional<Block> findBlockByColorRecursive(String color, List<Block> currentBlocks) {
		if (currentBlocks == null || currentBlocks.isEmpty()) {
			return Optional.empty();
		}
		
		for (Block block : currentBlocks) {
			if (block == null) {
				continue;
			}
			
			if (block.getColor() != null && block.getColor().equals(color)) {
				return Optional.of(block);
			}
			
			if (block instanceof CompositeBlock) {
				CompositeBlock compositeBlock = (CompositeBlock) block;
				Optional<Block> foundBlock = findBlockByColorRecursive(color, compositeBlock.getBlocks());
                if (foundBlock.isPresent()) {
                    return foundBlock;
                }
			}
		}
		return Optional.empty();	
	}

	@Override
	public List<Block> findBlocksByMaterial(String material) {
		if (material == null || blocks == null || blocks.isEmpty()) {
			return List.of();
		}
		return findBlocksByMaterialRecursive(material, blocks);
	}
	
	private List<Block> findBlocksByMaterialRecursive(String material, List<Block> currentBlocks) {
		List<Block> blocksOfCommonMaterial = new ArrayList<>();
		if (currentBlocks == null || currentBlocks.isEmpty()) {
			return blocksOfCommonMaterial;
		}
		
		for (Block block : currentBlocks) {
			 if (block == null) {
				 continue;
			 }
			
			if (block.getMaterial() != null && block.getMaterial().equals(material)) {
				blocksOfCommonMaterial.add(block);
			}
			
			if (block instanceof CompositeBlock) {
				CompositeBlock compositeBlock = (CompositeBlock) block;
				blocksOfCommonMaterial.addAll(findBlocksByMaterialRecursive(material, compositeBlock.getBlocks()));
			}
		}
		return blocksOfCommonMaterial;
	}
	

	@Override
	public int count() {
		if (blocks == null || blocks.isEmpty()) {
			return 0;
		}
		return countElementsInBlockRecursive(blocks);
	}
	
	private int countElementsInBlockRecursive(List<Block> currentBlocks) {
		if (currentBlocks == null || currentBlocks.isEmpty()) {
			return 0;
		}
		int counter = 0;
		
		for (Block block : currentBlocks) {	
			if (block instanceof CompositeBlock) {
				CompositeBlock compositeBlock = (CompositeBlock) block;
				counter += countElementsInBlockRecursive(compositeBlock.getBlocks());	
			}
			counter++;
		}
		return counter;
	}
	
	public List<Block> getBlocks() {
		return blocks;
	}

	public void setBlocks(List<Block> blocks) {
		this.blocks = blocks;
	}
	
}
